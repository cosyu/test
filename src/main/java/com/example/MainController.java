package com.example;

import com.example.async.AsyncClass;
import com.example.config.ApplicationContextProvider;
import com.example.config.MyProperties;
import com.example.report.Client;
import com.example.report.ClientByCountry;
import com.example.report.P1ResponseDTO;
import com.example.domain.User;
import com.example.retry.MyRetryable;
import com.example.retry.RemoteService;
import com.example.utils.EncryptUtils;
import com.example.utils.FileUtils;
import com.example.utils.JSONUtils;
import com.example.webmvcconfigurer.MyContentCachingRequestWrapper;
import com.example.yaml.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.aspectj.util.FileUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MainController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    //AppConfig will read the property file when Spring application is starting up
    //so that the value can be injected
    @Value("${session.server}")
    private String sessionServer;

    @Value("${resource.test.path}")
    private Resource testResource;//can be read as Resource

    //read the property and split it into list with ','
    @Value("#{'${esles.api.access}'.split(',')}")
    private List<String> eslesAccesses;

    @Autowired
    private AsyncClass asyncClass;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    @Qualifier("okHttpClient")
    private OkHttpClient okHttpClient;

    @Autowired
    private RemoteService remoteService;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    public Cache<String, String> cache;

    @PostConstruct
    public void init() {
        //util for semi-persistent, see also JSR-107 JCache in DCM project
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .initialCapacity(5)//5 concurrency hash table
                .maximumSize(50)
                .expireAfterWrite(1, TimeUnit.MINUTES)//not clear automatically, it will be cleared upon putting value
                .removalListener(notification -> System.out.println(notification.getKey()  + " is removed, cause: " + notification.getCause()))
                .build();

    }

    @GetMapping("/index")
    public String index(){
        return "Hello Test!";
    }

    @GetMapping("/requestPara")
    /*
    *  parameter:
    *  url?name=admin&password=123456
    *
    *  Content-Type in Request Header need to be application/x-www-form-urlencoded
    * */
    public String requestPara(@RequestParam(value = "name", required = true) String username,
                              @RequestParam(value = "password", required = true) String password){

        return "username:"+username+ "---password:"+password;
    }

    @PostMapping("/requestBody")
    @ResponseBody
    /**
     * raw json parameter:
     *
     *     {
     *         "name":"admin",
     *         "password":"123456"
     *     }
     *
     * Content-Type in Request Header need to be application/json
     *
     *     It will parse json to User object
     * */
    public ResponseEntity<?> requestBody(@RequestBody User user,HttpServletRequest request2) throws Exception{
        System.out.println(user.getName()+"--"+user.getPassword());

        //request in this class is always HttpServletRequest, it is initialized when this controller is created instead of request is sent.
        //request2 is from doFilter, please refer to WebInterceptor.java
        InputStream is = request.getInputStream();//request is not request2
        //to read input stream success, it should cache request body before, please refer to doFilter in WebInterceptor
        String str1 = IOUtils.toString(is, StandardCharsets.UTF_8);//str1 is "" for ContentCachingRequestWrapper in doFilter, not null for MyContentCachingRequestWrapper in doFilter
        System.out.println("controller request body...");
        System.out.println(str1);

        if(request2 instanceof ContentCachingRequestWrapper){//Spring request content caching wrapper
            System.out.println("request body of ContentCachingRequestWrapper...");
            String requestBody = new String(((ContentCachingRequestWrapper) request2).getContentAsByteArray());
            System.out.println(requestBody);
            String requestBody2 = new String(((ContentCachingRequestWrapper) request2).getContentAsByteArray());
            System.out.println(requestBody2);
        }

        if(request2 instanceof MyContentCachingRequestWrapper){//Customer request content caching wrapper
            System.out.println("request body of MyContentCachingRequestWrapper...");
            String requestBody = new String(((MyContentCachingRequestWrapper) request2).getBody());
            System.out.println(requestBody);
            String requestBody2 = new String(((MyContentCachingRequestWrapper) request2).getBody());
            System.out.println(requestBody2);
        }

        ResponseEntity<?> entity = new ResponseEntity(user, HttpStatus.OK);
        return entity;
        /*
        * It will return
        *      {
         *         "name":"admin",
         *         "password":"123456"
         *     }
         *   Content-Type in Response Header will be application/json
        * */
    }

    @PostMapping("/requestBody2")
    @ResponseBody
    /**
     * raw json parameter:
     * [
     *     {
     *         "name":"admin",
     *         "password":"123456"
     *     },
     *     {
     *         "name":"manager",
     *         "password":"88888"
     *     }
     * ]
     *
     * Content-Type in Request Header need to be application/json
     *
     * It will parse json to User object, and then add into List
     * */
    public ResponseEntity<?> requestBody2(@RequestBody List<User> userList){
        System.out.println(userList.toArray());
        ResponseEntity<?> entity = new ResponseEntity(userList, HttpStatus.OK);
        return entity;
        /*
        * It will return
        *
        * [
         *     {
         *         "name":"admin",
         *         "password":"123456"
         *     },
         *     {
         *         "name":"manager",
         *         "password":"88888"
         *     }
         * ]
         *
         * Content-Type in Response Header will be application/json
         *
        * */
    }

    /**
     * raw json parameter:
     *
     *     {
     *         "name":"admin",
     *         "password":"123456"
     *     }
     *
     * Content-Type in Request Header need to be application/json
     *
     *     It will parse json to User object
     * */
    @PostMapping("/postUser")
    public ResponseEntity<?> postUser(@RequestBody(required = true) User user){

        return new ResponseEntity(user, HttpStatus.OK);

    }

    @PostMapping("/postUser4")
    public ResponseEntity<?> postUser4(){

        User.UserBuilder builder = User.builder();
        User user = builder.id(1).name("Jason Yu").password("8888").build();
        ResponseEntity<?> entity = new ResponseEntity(user, HttpStatus.OK);
        return entity;

    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(){

        User.UserBuilder userBuilder = User.builder();
        User user = userBuilder.id(1).name("Jason").password("123").build();
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping("/postUser2")
    public ResponseEntity<?> postUser2(@RequestParam(required = true) int id){

        User.UserBuilder userBuilder = User.builder();
        User user = userBuilder.id(id).name("Jason").password("123").build();
        HttpHeaders httpHeaders = new HttpHeaders();
        //optional, no need to specify media type, default is application/json
        httpHeaders.setContentType(MediaType.parseMediaType("application/json"));
        return new ResponseEntity<>(user,httpHeaders, HttpStatus.OK);

    }

    @PostMapping("/postJsonObject")
    public JSONObject postJsonObject(@RequestBody User user){

        HttpServletRequest request = getRequest();
        Enumeration<String> heads =  request.getHeaderNames();

        while (heads.hasMoreElements()) {
            String headName = (String) heads.nextElement();
            String headValue = request.getHeader(headName);//根据请求头的名字获取对应的请求头的值
            System.out.println(headName+"-"+headValue);
        }

        JSONObject object = new JSONObject();
        object.put("name",user.getName());
        object.put("password",user.getPassword());
        return object;

    }

    @PostMapping("requestFile")
    @ResponseBody
    /*
    * Parameter name input in client(e.g. Postman) needs to be "files"(i.e. same as following parameter name),
    * if it is conducted in Postman, the parameters should be input in request body/form-data, select file as
    * parameter, the parameter should be same if it needs to select multiple files to upload.
    * Content-Type in Request Header needs to be multipart/form-data;boundary=<calculated when request is sent>
    *
    * */
    public ResponseEntity<?> requestFile(@RequestParam("files") MultipartFile[] uploadfiles,
                                         @RequestParam(value = "name", required = true) String username){

        System.out.println(Arrays.stream(uploadfiles).toArray());
        //return "username:"+username+ "---password:"+password;
        //ResponseEntity<?> entity = new ResponseEntity("成功上傳 -", HttpStatus.OK);
        ResponseEntity<?> entity = new ResponseEntity(uploadfiles, HttpStatus.OK);
        return entity;
        /*
        * It will return json file includes name, file bytes,size,timestamp,status,path and so on.
        *
        * Content-Type in Response Header will be application/json
        * */
    }

    @GetMapping("/testRequest")
    public Map testRequest(){

        //Cookie cookie = request.get
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        Cookie[] cookies = request.getCookies();
        Enumeration<String> heads =  request.getHeaderNames();

        Map map = new HashMap();
        while (heads.hasMoreElements()) {
            String headName = (String) heads.nextElement();
            String headValue = request.getHeader(headName);//根据请求头的名字获取对应的请求头的值
            map.put(headName,headValue);

        }


        Cookie c1 = new Cookie("cookie1", "value1");
        c1.setMaxAge(3600);c1.setPath("/testRequest");
        Cookie c2 = new Cookie("cookie2", "value2");
        c2.setMaxAge(3600);c2.setPath("/testRequest");

        HttpSession session = request.getSession();
        Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
        sessionCookie.setMaxAge(3600);sessionCookie.setPath("/testRequest");
        //it needs to send cookie to client by response so that request will send with cookie next time
        response.addCookie(c1);
        response.addCookie(c2);
        response.addCookie(sessionCookie);

        System.out.println(heads);
        return map;
    }

    @GetMapping("/testSession")
    public Map testSession(){

        HttpServletRequest request = getRequest();

        Enumeration<String> heads =  request.getSession().getAttributeNames();

        Map map = new HashMap();
        while (heads.hasMoreElements()) {
            String headName = (String) heads.nextElement();
            String headValue = request.getHeader(headName);
            map.put(headName,headValue);
        }
        return map;
    }

    @GetMapping("/testResponse")
    public Collection<String> testResponse(){

        HttpServletResponse response = getResponse();
        Collection<String> heads = response.getHeaderNames();

        System.out.println(heads);
        LocaleContextHolder.getLocaleContext().getLocale();
        return heads;
    }

    @PostMapping("/testToken")
    public ResponseEntity<Map<String, String>> issueToken() {
        //String token = jwtService.generateToken(request);
        Map<String, String> response = Collections.singletonMap("token", "afdsafdsafasfafsafsa14225");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/async")
    public void testAsync() throws Exception{

        System.out.println("--------start run controller---------current thread:" + Thread.currentThread().getName());
        asyncClass.asyncMethod();
        System.out.println("--------run controller end---------current thread:"+Thread.currentThread().getName());

    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    @GetMapping("/property")
    public String property() throws Exception{

        String res = System.getProperty("file.encoding");
        String res2 = System.getProperty("common.properties");
        String res3 = Charset.defaultCharset().toString();
        String res4 = System.getProperty("user.timezone");
        System.out.println(res);
        System.out.println(res2);
        System.out.println(res3);
        System.out.println(res4);

        String filePath = "C:\\Users\\Dell\\IdeaProjects\\TestStart\\log\\test.log";
        String secretKey = "uBdUx82vPHkDKb284d7NkjFoNcKWBuka";
        String content = "AAAADJNzQVWoJ6AYLsEHtyzdz4WSUO9VL4iP8FOWcsmG4Nay9w5rcMsyBOg34Jt3ldbhwvD8vxOxu+0spOm4S78a7mTzQarRhvwNuxwXu8hUp1Q=";
        content = "AAAADDZha4GZVX1rfYYZNJnpr4lpF1Ix5Kc7giTD65sfvC+5kPvpZGmK83qj/R8K8PweeZ32jIHvn/7rg3N/7oA+3UzKmFQsrTsZVhdnHWphmRmm";
        String s =  new String(EncryptUtils.aesDecrypt(Base64.getDecoder().decode(content),
                secretKey.getBytes()), StandardCharsets.UTF_8);
        String s2 =  new String(EncryptUtils.aesDecrypt(Base64.getDecoder().decode(content),
                secretKey.getBytes()));//it uses default charset to create string , may show 爛字 for Chinese
        System.out.println(s);
        System.out.println(s2);
        FileUtils.writeStringToNewFile(filePath,"");
        FileUtils.appendStringToFile(filePath,s);
        FileUtils.appendStringToFile(filePath,"\n"+s2);

        return "complete";
    }

    @GetMapping("/locale")
    public String locale(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Locale locale = LocaleContextHolder.getLocale();
        //args is for parameter in the message property file
        return messageSource.getMessage("current.locale", new String[] {"value1","value2"}, locale);
    }

    //@PostMapping("/doLogin")
    @GetMapping("/doLogin")
    public String login(HttpServletRequest request, HttpServletResponse response){

        //Session will be created automatically if client side send request first time or the current session
        //is expired, but developer is used to save login user in the session.
        request.getSession().setAttribute("username","Jason");
        return "Jason";
    }


    @GetMapping("/queryUser")
    public String queryUser(HttpServletRequest request, HttpServletResponse response){

        String userName = (String) request.getSession().getAttribute("username");
        String csrfToken = request.getHeader("XSRF-TOKEN");
        return "Current username is:"+userName+" -session id:"+request.getSession().getId()+"-csrfToken: "+csrfToken;
    }

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        /*destroy current session, it will create another new session
        and save it to Redis(if application is using Redis to save session) if client send request again
        */
        request.getSession().removeAttribute("username");
        request.getSession().invalidate();
        return "do logout";
    }

    @GetMapping("/displayLogout")
    public String displayLogout(){

        return "logout";
    }

    @GetMapping("/hello")
    public String hello(){

        return "hello";
    }

    @GetMapping("/resSts1")
    public ResponseEntity<String> resStatus1(){

        return new ResponseEntity<>("completed", HttpStatus.OK);//return with HttpStatus
    }

    @GetMapping("/resSts2")
    @ResponseStatus(code = HttpStatus.ACCEPTED) //return with HttpStatus
    public String resStatus2(){

        return "completed";
    }

    @GetMapping("/resSts4")
    @ResponseStatus(code = HttpStatus.ACCEPTED)//specify response HttpStatus even the function no return value
    public void resStatus4(){

    }

    @GetMapping("/resSts3")
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "fail,internal server error")//return with HttpStatus,reason is only for error
    public String resStatus3() throws Exception{
        return "complete";
    }

    @GetMapping("/addCookie")
    public String addCookie(){

        response.setContentType("text/html;charset=utf-8");
        //String id = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("customerId", "123456");
        Cookie cookie2 = new Cookie("customerId", "123456789099");
        /*
        Add cookie to response, then browser can save this cookie upon receiving response,
        this cookie will be set with request next time, server can get the cookie from request header or cookie by name
        **/
        response.addCookie(cookie);
        response.addCookie(cookie2);//if cookie name is existing, it will overwrite the before one

        return "completed";
    }

    @GetMapping("/addCookie2")
    public void addCookie2(){

        String cookieValue = String.format("%s=%s;Max-Age=%s;domain=%s;path=/;HTTPOnly;Secure",
                    "customerId2", "8888888", "1800", "www.localhost.com");
        response.addHeader("Set-Cookie", cookieValue);//another way to add cookie

    }

    @GetMapping("/deleteCookie")
    public String deleteCookie(){

        response.setContentType("text/html;charset=utf-8");

        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("customerId")) {
                    cookie.setMaxAge(0);//browser will delete cookie upon receiving response
                    response.addCookie(cookie);
                }
            }
        }

        return "completed";
    }

    @GetMapping("/testRedis")
    public void testRedis(){

        redisTemplate.opsForHash().put("key", "map1", "value1");
        redisTemplate.opsForHash().put("key", "map2", "value2");

        redisTemplate.opsForHash().put("key1", "map1", "value11");
        redisTemplate.opsForHash().put("key1", "map2", "value22");

        String value = (String) redisTemplate.opsForHash().get("key1", "map1");
        System.out.println(value);

    }

    @GetMapping("/testApi")
    public ResponseEntity<User> testApi(){

        List<String> tokens = new ArrayList<>();
        tokens.add("1234");
        tokens.add("abcde");
        tokens.add("aafdsafa");
        System.out.println(tokens.stream().collect(Collectors.joining("|")));

        User user = new User(1,"JASON","123456");
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping("/testApi2")
    public ResponseEntity<List<User>> testApi2(){

        User user = new User(1,"JASON","123456");
        User user2 = new User(2,"Jack","678987");
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user2);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @GetMapping("/testMatch")
    public String testMatch( @RequestParam(value = "url", required = true) String url){

        /**
         * url need to match with the following express
         * /api/123.jsp will match with /api/*.jsp but http://xxx.com/api/123.jsp does not, it is different from java regex
         * please compare with testRegExp method
         * */
        String urls = "/ap?/auth/signin,/api/*.jsp,/api/account/passwd/**,/api/file/reg/**/*.jsp";
        String[] checkCsrfUrls = urls.split(",");
        AntPathMatcher matcher = new AntPathMatcher();//Spring util

        if (Arrays.stream(checkCsrfUrls).anyMatch(p -> matcher.match(p, url))) {
            return "match";
        }else{
            return "not match";
        }
    }

    @GetMapping("/testMatch2")
    public String testMatch2(@RequestParam(value = "mail", required = true) String mailAddress){
        String[] whitelist = "tswp2-dev.hk".split(",");
        if(Arrays.stream(whitelist).anyMatch(mailAddress::contains)){//mail needs to contain tswp2-dev.hk
            return  "match";
        }else{
            return  "not match";
        }

    }

    @GetMapping("/testRegExp")
    public String testRegExp(@RequestParam(value = "url",required = true) String url){

        /**
        * url need to match with specify express
        * https://xxx.com/pga/api/applicaitons && pga/api/applicaitons  will match with /pga/api/applications$
        * please compare with testMatch method
        * */
        boolean match = false;
        for (String api : eslesAccesses) {

            Pattern pattern = Pattern.compile(api);
            Matcher matcher = pattern.matcher(url);

            if (matcher.find()) {
                match = true;
                break;
            }
        }

        if(match){
            return "match";
        }
        return "not match";
    }

    @GetMapping("/testWriter")
    public void testWriter() throws Exception{

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Map<String, Object> result = new HashMap<>();

        result.put("code", "403");
        result.put("status", httpStatus.value());
        result.put("message", "can not access");
        result.put("title", httpStatus.getReasonPhrase());
        result.put("path", "/test");

        String content = JSONUtils.toJSON(result);

        response.setStatus(httpStatus.value());

        PrintWriter out = response.getWriter();
        out.print(content);//it will output the content(e.g. json) to client side without returning value
        out.flush();
        out.close();

    }

    @GetMapping("/getFile")
    @CrossOrigin(exposedHeaders = "content-disposition")
    public ResponseEntity<ByteArrayResource> getFile(@RequestParam("type") String type) throws Exception {

        //export file to client side
        File file1 = new File("C:\\Users\\Dell\\Desktop\\Notes\\example.pdf");
        ByteArrayResource res = new ByteArrayResource(FileUtil.readAsByteArray(file1));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        httpHeaders.add("content-disposition", "inline; filename=document.pdf");
        httpHeaders.setContentLength(res.contentLength());

        return new ResponseEntity<>(res,httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/getFile2")
    @CrossOrigin(exposedHeaders = "content-disposition")
    public ResponseEntity<ByteArrayResource> getFile2() throws Exception {

        File file1 = new File("C:\\Users\\Dell\\Desktop\\Notes\\response.json");
        String content = org.apache.commons.io.FileUtils.readFileToString(file1,StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        P1ResponseDTO p1ResponseDTO = objectMapper.readValue(content, P1ResponseDTO.class);
        //String str = new String(Base64.getDecoder().decode(p1ResponseDTO.getData()), StandardCharsets.UTF_8);

        ByteArrayResource res = new ByteArrayResource(Base64.getDecoder().decode(p1ResponseDTO.getData()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        httpHeaders.add("content-disposition", "inline; filename=document.pdf");
        httpHeaders.setContentLength(res.contentLength());

        return new ResponseEntity<>(res,httpHeaders, HttpStatus.OK);
    }

    //Jasper export file
    @GetMapping("/report")
    @CrossOrigin(exposedHeaders = "content-disposition")
    public ResponseEntity<ByteArrayResource> report(@RequestParam(value = "type",required = true) String type) throws Exception {

        String reportOutput = "C:\\Users\\Dell\\Desktop\\Notes\\report-demo";
        String fileType = ".pdf";
        if(!"pdf".equals(type)){
            fileType = ".xls";//Jasper is not compatible with .xlsx
        }
        reportOutput += fileType;

        List<Client> clientList = new ArrayList<>();
        Client.ClientBuilder builder = Client.builder().id(1).name("Jason Yu").country("HK");
        Client.ClientBuilder builder2 = Client.builder().id(2).name("Jason Chan").country("China");
        clientList.add(builder.build());
        clientList.add(builder2.build());

        //JRBeanCollectionDataSource clientDS = new JRBeanCollectionDataSource(clientList);
        //JRBeanCollectionDataSource clientByCountryDS = new JRBeanCollectionDataSource(clientByCountryList);

        //add them to the parameters Map object
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("title", "Report Demo");
        //parameters.put("CLIENT_DS", clientDS);
        //parameters.put("BY_COUNTRY_DS", clientByCountryDS);

        // Call fillReport method passing the main report and the parameters
        JasperDesign design = JRXmlLoader.load(new ClassPathResource("/client-list.jrxml").getInputStream());
        JasperReport report = JasperCompileManager.compileReport(design);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                //read files from resource path new ClassPathResource("/client-list.jasper").getInputStream() new JRBeanCollectionDataSource(clientList)
                report, parameters,  new JRBeanCollectionDataSource(clientList));

        JRAbstractExporter exporter = new JRPdfExporter();
        if(!"pdf".equals(type)){
            exporter = new JRXlsExporter();
        }
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportOutput));
        if(!"pdf".equals(type)){
            SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
            xlsReportConfiguration.setOnePagePerSheet(false);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(true);
            xlsReportConfiguration.setDetectCellType(true);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsReportConfiguration.setFontSizeFixEnabled(false);
            exporter.setConfiguration(xlsReportConfiguration);
        }

        exporter.exportReport();

        //export file to client side
        File file1 = new File(reportOutput);
        ByteArrayResource res = new ByteArrayResource(FileUtil.readAsByteArray(file1));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        if(!"pdf".equals(type)){
            httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        }
        httpHeaders.add("content-disposition", "inline; filename=report-demo"+fileType);//for display file in browser
        //httpHeaders.add("content-disposition", "attachment; filename=report-demo"+fileType);//for download file directly
        httpHeaders.setContentLength(res.contentLength());

        return new ResponseEntity<>(res,httpHeaders, HttpStatus.OK);
    }

    //Jasper export file
    @GetMapping("/report2")
    @CrossOrigin(exposedHeaders = "content-disposition")
    public ResponseEntity<ByteArrayResource> report2() throws Exception {

        String reportOutput = "C:\\Users\\Dell\\Desktop\\Notes\\report-demo2.pdf";

        List<Client> clientList = new ArrayList<>();
        Client.ClientBuilder builder = Client.builder().id(1).name("Jason Yu").country("HK");
        Client.ClientBuilder builder2 = Client.builder().id(2).name("Jason Chan").country("China");
        clientList.add(builder.build());
        clientList.add(builder2.build());

        List<ClientByCountry> clientByCountryList = new ArrayList<>();
        ClientByCountry.ClientByCountryBuilder clientByCountryBuilder =
                ClientByCountry.builder().country("HK").total(10);
        ClientByCountry.ClientByCountryBuilder clientByCountryBuilder2 =
                ClientByCountry.builder().country("China").total(20);
        clientByCountryList.add(clientByCountryBuilder.build());
        clientByCountryList.add(clientByCountryBuilder2.build());

        JRBeanCollectionDataSource clientDS = new JRBeanCollectionDataSource(clientList);
        JRBeanCollectionDataSource clientByCountryDS = new JRBeanCollectionDataSource(clientByCountryList);

        //add them to the parameters Map object
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("title", "Report Demo");
        parameters.put("CLIENT_DS", clientDS);
        parameters.put("BY_COUNTRY_DS", clientByCountryDS);

        // Call fillReport method passing the main report and the parameters
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                //read files from resource path new ClassPathResource("/client-list.jasper").getInputStream() new JRBeanCollectionDataSource(clientList)
                new ClassPathResource("/clients-example.jasper").getInputStream(), parameters,  new JREmptyDataSource());

        JRAbstractExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportOutput));
        exporter.exportReport();

        //export file to client side
        File file1 = new File(reportOutput);
        ByteArrayResource res = new ByteArrayResource(FileUtil.readAsByteArray(file1));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        httpHeaders.add("content-disposition", "inline; filename=report-demo2.pdf");//for display file in browser
        //httpHeaders.add("content-disposition", "attachment; filename=report-demo2.pdf");//for download file directly
        httpHeaders.setContentLength(res.contentLength());

        //AAAADLdJhifm16F8FfWV51h76J7OMjqqupuyDgsF65uEfNiTercxSKa6FSeTyBp3ivxhp1YbHdQHNgtCtN5ocA==
        return new ResponseEntity<>(res,httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/testCache1")
    public void testCache1(){
        cache.put("key1","value1");
    }

    @GetMapping("/testCache2")
    public String testCache2(){
        if(cache.getIfPresent("key1") == null){
            return "not found";
        }else{
            return "found";
        }
    }

    @PostMapping("/testValidation")
    public ResponseEntity<?> testValidation(@Validated @RequestBody User user) { // perform validation for User
        System.out.println(user.getName() + "--" + user.getPassword());
        ResponseEntity<?> entity = new ResponseEntity(user, HttpStatus.OK);
        return entity;
    }

    @GetMapping("/testRetry")
    @MyRetryable(maxAttempts = 1, value = RuntimeException.class)
    public void testRetry() {
        System.out.println("test retry...");
        throw new RuntimeException("runtime exception...");
    }

    @GetMapping("/testRetry2")
    public void testRetry2(){
        remoteService.call();
    }

    @GetMapping("/testYaml")
    public ResponseEntity<?> testYaml(){

        MySystemProperty mySystemProperty = ApplicationContextProvider.getBean(MySystemProperty.class);
        System.out.println(mySystemProperty.getName()+"--"+mySystemProperty.getVersion());
        return new ResponseEntity<>(mySystemProperty, HttpStatus.OK);
    }

    @GetMapping("/testYaml2")
    public ResponseEntity<?> testYaml2(){

        YamlStatusProperties mySystemProperty = ApplicationContextProvider.getBean(YamlStatusProperties.class);
        return null;
    }

    @GetMapping("/testYaml3")
    public ResponseEntity<?> testYaml3(){

        YamlStatusProperties2 mySystemProperty = ApplicationContextProvider.getBean(YamlStatusProperties2.class);
        //this object will be like this
        /*
           Q24: [APPROVE,PROC,REJECT,RETURN,COMPLETE]
        * */
        Map<String, List<PgaAppStatus>> application = mySystemProperty.getApplication();
        //this object will be like this
        /*
           Q24: [APPROVE,PROC,REJECT,RETURN,COMPLETE]
           status.Q24: [APPROVE,PROC,REJECT,RETURN,COMPLETE]  duplicate for status,
           as it's field name is documentStatus instead of document
        * */
        Map<String, List<PgaDocStatus>> documentStatus = mySystemProperty.getDocumentStatus();
        return null;
    }

    @GetMapping("/img2str")
    public String img2str() throws Exception{

        String imagePath = "C:\\Users\\Dell\\Desktop\\tsw images\\identity document.JPG";
        byte[] fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(new File(imagePath));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);//file to base64 string

        String outPutImagePath = "C:\\Users\\Dell\\Desktop\\tsw images\\identity document2.JPG";
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(outPutImagePath), decodedBytes);//base64 string to file

        return encodedString;
    }

    @GetMapping("/testLog")
    public void testLog() {
        log.debug("test log debug output.....");//this will not output, please refer to logback-spring.xml
        log.info("test log info output.....");
        log.warn("test log warn output....");
        log.error("test log error output...");
    }

    @GetMapping("/testRes")
    public String testRes() throws Exception{

        return new String(FileUtil.readAsByteArray(testResource.getInputStream()));

    }

    //Test OAuth
    @GetMapping("/index.html")
    public String oAuth(String code) {

        String res = "";
        if (code != null) {

            RestTemplate restTemplate = (RestTemplate)ApplicationContextProvider.getBean("SSLRestTemplate");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "javaboy");
            map.add("client_secret", "123456");//access token need secret while code does not
            map.add("redirect_uri", "http://localhost:8090/index.html");
            map.add("grant_type", "authorization_code");//code mode
            //please refer to Test-OAuth-Auth-Service
            //get access token with returned code from Authorization Server
            Map<String,String> resp = restTemplate.postForObject("http://localhost:8180/oauth/token", map, Map.class);
            String access_token = resp.get("access_token");//if this access token is modified, it will throw 401 Unauthorized
            System.out.println("access token of authorization: " + access_token);//54f4cd76-0c2b-4ddf-b53a-7bb2c0d98134

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + access_token);//remember this is bearer token
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            //please refer to Test-OAuth-Auth-Resource
            //get resource with access token from Resource Server
            //It will check the token is valid or not in Resource Server
            ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8181/admin/hello", HttpMethod.GET, httpEntity, String.class);
            res = entity.getBody();
        }

        return "<!DOCTYPE html>\n" +
                "<html lang='en' xmlns:th='http://www.thymeleaf.org'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>OAuth</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Hello, welcome to OAuth！\n" +
                "\n" +
                "<a href='http://localhost:8180/oauth/authorize?client_id=javaboy&response_type=code&redirect_uri=http://localhost:8090/index.html'>第三方登录</a>\n" + //request auth code with client id
                "\n" +
                "<h1>"+res+"</h1>\n" +
                "</body>\n" +
                "</html>";
    }


    @GetMapping("/template")
    public String template() {
        String templatePath = "mail";
        Context ctx = new Context();
        ctx.setVariable("message", "This is my message");
        ctx.setVariable("creatorFullName", "Chan Tai Man");
        return springTemplateEngine.process(templatePath, ctx);
    }

    @GetMapping("/property2")
    public String property2() {
        String mailSubjectPrefix = MyProperties.getProperty("tsw.notification.template.mail.subject.prefix");
        String subject = MyProperties.getProperty("tsw.notification.template.R01.en.subject");
        String storeType = MyProperties.getProperty("spring.session.store-type");
        String systemName = MyProperties.getProperty("system.name");
        String status = MyProperties.getProperty("yaml.application.Q24[0]");
        return  mailSubjectPrefix+"--"+ subject+"--"+storeType+"--"+systemName+"--"+status;
    }

}

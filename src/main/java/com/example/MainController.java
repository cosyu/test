package com.example;

import com.example.async.AsyncClass;
import com.example.test.User;
import com.example.utils.EncryptUtils;
import com.example.utils.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class MainController {

    //AppConfig will read the property file when Spring application is starting up
    //so that the value can be injected
    @Value("${session.server}")
    private String sessionServer;

    @Autowired
    private AsyncClass asyncClass;

    @Autowired
    private MessageSource messageSource;

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
     * json parameter:
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
    public ResponseEntity<?> requestBody(@RequestBody User user){
        System.out.println(user.getName()+"--"+user.getPassword());
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
     * json parameter:
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

    @PostMapping("/postUser")
    public String postUser(@RequestBody(required = true) User user){

        return user.getName()+"--"+user.getPassword();

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

    @GetMapping("/logout")
    public String logout(){

        return "logout";
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

}

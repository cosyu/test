package com.example;

import com.example.async.AsyncClass;
import com.example.test.User;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@RestController
public class MainController {


    @Autowired
    private AsyncClass asyncClass;

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
}
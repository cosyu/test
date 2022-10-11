package com.example.resettemplate;

import com.example.MainApplication;
import com.example.config.ApplicationContextProvider;
import com.example.domain.User;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@SpringBootApplication
public class RestTemplateTest {

    public static void main(String... args) throws Exception{

        System.out.println("--- Start to Run Spring Application---");
        ApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
        System.out.println("--- Run Spring Application Successfully---");

        //it used to call API with synchronous
        RestTemplate restTemplate = (RestTemplate)ApplicationContextProvider.getBean("SSLRestTemplate");

        //Map<String, String> vars = Collections.singletonMap("hotel", "42");
        //call get
        String result = restTemplate.getForObject("http://localhost:8090/index", String.class);
        System.out.println(result);

        //call post
        String url = "http://localhost:8090/postUser";
        //JSONObject param = new JSONObject();
        //Map param = new HashMap();
        //param.put("name","jason");
        //param.put("password","123456");
        User user = new User();
        user.setName("jason");
        user.setPassword("123456");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,user,String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        HttpHeaders headers = responseEntity.getHeaders();
        System.out.println(responseEntity.getBody());

        //Add header for post
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://localhost:8090").
                path("/postJsonObject").build(true);
        URI uri = uriComponents.toUri();

        RequestEntity<User> requestEntity = RequestEntity.post(uri).
                        header(HttpHeaders.COOKIE,"key1=value1").
                        header("MyRequestHeader", "MyValue").
                        accept(MediaType.APPLICATION_JSON).
                        contentType(MediaType.APPLICATION_JSON).
                        body(user);
        ResponseEntity<JSONObject> responseEntity2 = restTemplate.exchange(requestEntity,JSONObject.class);
        JSONObject responseEntityBody = responseEntity2.getBody();
        System.out.println(responseEntityBody);

    }

}

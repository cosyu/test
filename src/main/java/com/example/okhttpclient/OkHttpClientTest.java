package com.example.okhttpclient;

import okhttp3.*;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OkHttpClientTest {


    public static void main(String[] args) throws Exception{

        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8090";

        //sync get
        Request request = new Request.Builder()
                .url(url+"/getUser")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
           System.out.println(response.body().string());
           /**
           response.body().string()只能调用一次，在第一次时有返回值，第二次再调用时将会返回null。
           原因是：response.body().string()的本质是输入流的读操作，必须有服务器的输出流的写操作时客户端的读操作才能得到数据。
           而服务器的写操作只执行一次，所以客户端的读操作也只能执行一次，第二次将返回null。
           * */
            //System.out.println("second time to get response body"+response.body().string());
        }

        //sync post with request body
        JSONObject postParam = new JSONObject();
        postParam.put("id",2);
        postParam.put("name","Chan Tai Man");
        postParam.put("password","123456");
        //request header needs content type is application/jason
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                                postParam.toString());
        request = new Request.Builder()
                .url(url+"/postUser")
                .post(requestBody)
                .build();
        response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body().string());
        }

        //async get
        /**
         * 这是异步请求，所以调用enqueue则无需再开启子线程，enqueue方法会自动将网络请求部分放入子线程中执行。
         * enqueue回调方法onResponse与onFailure都执行在子线程中。
         * */
        request = new Request.Builder()
                .url(url+"/getUser")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new OkHttpCallback());

        //async post
        requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                                postParam.toString());
        request = new Request.Builder()
                .url(url+"/postUser")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new OkHttpCallback());

    }

    static class OkHttpCallback implements Callback {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            System.out.println(e);
        }

        /**
        回调接口的onFailure方法和onResponse执行在子线程。
        response.body().string()本质是输入流的读操作，所以它还是网络请求的一部分，所以这行代码必须放在子线程。
        * */
        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if (response.isSuccessful()) {
                System.out.println("Successful data acquisition . . . ");
                System.out.println("response.code()==" + response.code());
                System.out.println("response.body().string()==" + response.body().string());
            }else{
                System.out.println(response);
            }
        }
    }

}

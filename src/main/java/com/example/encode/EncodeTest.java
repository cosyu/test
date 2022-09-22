package com.example.encode;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeTest {


    public static void main(String... args) throws Exception{

        //String encode & decode
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final String text = "Jason Yu";
        final byte[] textByte = text.getBytes("UTF-8");

        final String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);

        System.out.println(new String(decoder.decode(encodedText), "UTF-8"));

        //URl encode && decode
        String redirectURI = "https://www.xxx.com/eid-login/ api/auth";
        String enCodeUrl = URLEncoder.encode(redirectURI, StandardCharsets.UTF_8);
        System.out.println("enCodeUrl: "+enCodeUrl);
        String deCodeUrl = URLDecoder.decode(enCodeUrl,StandardCharsets.UTF_8);
        System.out.println("deCodeUrl:"+deCodeUrl);

        String url1 = "eidapi_auth%20eidapi_formFilling%20eidapi_sign%20eidapi_fr";
        System.out.println("deCodeUrl2:"+URLDecoder.decode(url1,StandardCharsets.UTF_8));

    }

}

package com.example.resettemplate;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class RestClientConfig {

    @Bean("SSLRestTemplate")
    //@Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public RestTemplate restOperations(ClientHttpRequestFactory clientHttpRequestFactory) throws Exception {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        //set interceptor
        ResetTemplateInterceptor myInterceptor = new ResetTemplateInterceptor();
        List<ClientHttpRequestInterceptor> list = new ArrayList<>();
        list.add(myInterceptor);
        restTemplate.setInterceptors(list);

        //set error handler
        restTemplate.setErrorHandler(new ResetTemplateResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(@Qualifier("httpClient2") HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        // timeout
        requestFactory.setReadTimeout(60 * 1000);
        requestFactory.setConnectTimeout(60 * 1000);
        return requestFactory;
    }

    @Bean("httpClient2") // it will create bean by Spring and used for clientHttpRequestFactory when the application is starting up
    public HttpClient httpClient(){
        return HttpClients.createDefault();
    }

    @Bean("okHttpClient") // it will create bean by Spring and used for clientHttpRequestFactory when the application is starting up
    public OkHttpClient okHttpClient(){

        OkHttpClient client = new OkHttpClient();
        return client.newBuilder().addNetworkInterceptor(new LogInterceptor()).build();

    }

    //Interceptor for OkHttpClient
    class LogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            log.debug(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            log.debug(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    /*
    @Bean("httpClient2")
    public HttpClient httpClient(@Value("${ssl.client.truststore.path}") Resource file, @Value("${ssl.client.truststore.password}") String password) throws Exception {
        //String keystorePassword = password.getDescrambled();
        log.debug("truststore.path = {}", file);

        if (!file.exists()) {
            return HttpClients.custom().build();
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream instream = file.getInputStream();
        InputStream instreamKey = file.getInputStream();
        try {
            keyStore.load(instreamKey, password.toCharArray());
            trustStore.load(instream, password.toCharArray());

            kmf.init(keyStore, password.toCharArray());
            tmf.init(trustStore);

        } finally {
            instream.close();
            instreamKey.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .loadKeyMaterial(trustStore, password.toCharArray()).build();

        SSLSocketFactory socketFactory = sslcontext.getSocketFactory();
        //SSLSocket socket = (SSLSocket) socketFactory.createSocket();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1.2" }, null, new NoopHostnameVerifier());

        return HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()) // HostnameVerifier NoopHostnameVerifier
                .setSSLSocketFactory(sslsf)
                .build();
    }
    */
}

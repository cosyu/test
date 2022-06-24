package com.example.resettemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

//Interceptor for RestTemplate, it will do something before RestTemplate calls API
public class MyInterceptor implements ClientHttpRequestInterceptor {

    private final static String[] propagateHeaders = {
            "x-request-id",
            "x-b3-traceid",
            "x-b3-spanid",
            "x-b3-parentspanid",
            "x-b3-sampled",
            "x-b3-flags",
            "b3",
            "x-ot-span-context",
            "x-cloud-trace-context",
            "traceparent",
            "grpc-trace-bin"
    };

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null && (requestAttributes instanceof ServletRequestAttributes)) {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpHeaders headers = request.getHeaders();
            for (String header : propagateHeaders) {
                Enumeration<String> values = servletRequest.getHeaders(header);
                if (values != null) {
                    for (;values.hasMoreElements();) {
                        headers.add(header, values.nextElement());
                    }
                }
            }
        }

        return execution.execute(request,body);
    }
}

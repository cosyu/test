package com.example.webmvcconfigurer;

/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

//customer request wrapper for caching request body which is only can be called one time
public class MyContentCachingRequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    public MyContentCachingRequestWrapper(HttpServletRequest request) throws IOException
    {
        super(request);

        //read request body and cache
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();//it only can get one time, otherwise, it will throw exception
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        InputStream inputStream2 = request.getInputStream();
        //Store request body content in 'body' variable
        body = stringBuilder.toString();
    }

    //return InputStream base on request body which is get before.
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            private ReadListener listener = null;
            private boolean alreadyNotify = false;

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() <= 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                if (this.listener != null) {
                    throw new IllegalStateException("setReadListener is called more than once within the scope of the same request");
                }
                this.listener = listener;
                try {
                    listener.onDataAvailable();
                } catch (IOException e) {
                }
            }

            @Override
            public int read() throws IOException {
                int data = byteArrayInputStream.read();//Reads the next byte of data from this input stream.
                if (data == -1 && listener != null && !alreadyNotify) {
                    listener.onAllDataRead();
                    alreadyNotify = true;
                }
                return data;
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (getCharacterEncoding() == null) {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        } else {
            return new BufferedReader(new InputStreamReader(this.getInputStream(), getCharacterEncoding()));
        }
    }

    //Use this method to read the request body N times
    public String getBody() {
        return this.body;
    }
}


package com.example.springlistener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//Session is outdated, suggest to use token instead of session for login user
@WebListener
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(300);
        System.out.println("session created,session id is:"+se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String userName = (String)se.getSession().getAttribute("username");
        System.out.println("session destroy,session id is:"+se.getSession().getId()+" user name is"+userName);
    }
}

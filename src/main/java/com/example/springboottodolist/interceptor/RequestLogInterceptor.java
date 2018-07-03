package com.example.springboottodolist.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestLogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Request URL: {} {}", request.getMethod(), request.getRequestURL());
        return true;
    }
}

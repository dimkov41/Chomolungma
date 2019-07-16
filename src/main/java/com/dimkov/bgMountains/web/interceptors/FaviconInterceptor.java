package com.dimkov.bgMountains.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String link = "https://banner2.kisspng.com/20180419/clq/kisspng-mountain-clip-art-mountain-logo-5ad8515ca033b7.3103192015241260446562.jpg";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
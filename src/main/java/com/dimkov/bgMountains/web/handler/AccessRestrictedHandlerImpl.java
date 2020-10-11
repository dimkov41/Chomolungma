package com.dimkov.bgMountains.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AccessRestrictedHandlerImpl implements AccessDeniedHandler {
    private static final Logger log = LoggerFactory.getLogger(AccessRestrictedHandlerImpl.class);
    private static final String INDEX_PAGE_URL = "/";

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("User: {} attempted to access the protected URL: {}", authentication.getName(), httpServletRequest.getRequestURI());
        }
        httpServletResponse.sendRedirect(INDEX_PAGE_URL);
    }

}

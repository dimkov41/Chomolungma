package com.dimkov.bgMountains.web.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AccessRestrictedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
//        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        //TODO: implement logger
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
//            logger.log(Level.INFO, String.format(
//                    "User: %s attempted to access the protected URL: %s",
//                    authentication.getName(), httpServletRequest.getRequestURI()));
        }

        //TODO: CHANGE URL
        httpServletResponse.sendRedirect("/home");
    }

}

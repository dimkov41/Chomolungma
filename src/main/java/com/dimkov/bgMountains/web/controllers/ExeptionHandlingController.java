package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExeptionHandlingController extends BaseController {
    private static final String ELEMENT_NOT_FOUND_IMG_PATH = "/images/ic_notfound.png";

    private static final String NOT_FOUND_PATH = "errorPages/notFound";

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNoSuchElement(HttpServletRequest req, Exception ex) {
//        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constants.MODEL_ATTR_NAME, ELEMENT_NOT_FOUND_IMG_PATH);
        modelAndView.addObject(Constants.EXEPTION_MESSAGE_ATTR_NAME, ex.getMessage());

        return view(NOT_FOUND_PATH, modelAndView);
    }
}

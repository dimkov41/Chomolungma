package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.util.Constants;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExeptionHandlingController extends BaseController {
    private static final String NOT_FOUND_IMG_PATH = "/images/ic_notfound.png";
    private static final String TRY_AGAIN_IMG_PATH = "/images/tryAgain.jpg";

    private static final String NOT_FOUND_VIEW = "errorPages/errorPage";

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNoSuchElement(Exception ex) {
//        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constants.MODEL_ATTR_NAME, NOT_FOUND_IMG_PATH);
        modelAndView.addObject(Constants.EXEPTION_MESSAGE_ATTR_NAME, ex.getMessage());

        return view(NOT_FOUND_VIEW, modelAndView);
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleExeption(Throwable th) {
//        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constants.MODEL_ATTR_NAME, TRY_AGAIN_IMG_PATH);
        modelAndView.addObject(Constants.EXEPTION_MESSAGE_ATTR_NAME, th.getMessage());

        return view(NOT_FOUND_VIEW, modelAndView);
    }
}

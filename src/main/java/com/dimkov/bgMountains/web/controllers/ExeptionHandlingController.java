package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.web.annotations.PageTitle;
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
    @PageTitle("Error occurred")
    public ModelAndView handleNoSuchElement(Exception ex) {
        return handleExeption(NOT_FOUND_IMG_PATH, ex);
    }

    @ExceptionHandler(Throwable.class)
    @PageTitle("Error occurred")
    public ModelAndView handleExeption(Throwable th) {
        return handleExeption(TRY_AGAIN_IMG_PATH, th);
    }

    private ModelAndView handleExeption(String imgPath, Throwable th){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constants.MODEL_ATTR_NAME, imgPath);
        modelAndView.addObject(Constants.EXEPTION_MESSAGE_ATTR_NAME, th.getMessage());

        return view(NOT_FOUND_VIEW, modelAndView);
    }
}

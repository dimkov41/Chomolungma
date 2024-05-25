package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.web.annotations.PageTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

import static com.dimkov.bgMountains.util.Constants.*;

@ControllerAdvice
public class ExeptionHandlingController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(PeakController.class);
    private static final String NOT_FOUND_IMG_PATH = "/images/ic_notfound.png";
    private static final String TRY_AGAIN_IMG_PATH = "/images/tryAgain.jpg";

    private static final String NOT_FOUND_VIEW = "errorPages/errorPage";

    @ExceptionHandler(NoSuchElementException.class)
    @PageTitle("Error occurred")
    public ModelAndView handleNoSuchElement(Exception ex) {
        log.error(ex.getMessage(),ex);
        return handleExeption(NOT_FOUND_IMG_PATH, ex);
    }

    @ExceptionHandler(Throwable.class)
    @PageTitle("Error occurred")
    public ModelAndView handleExeption(Throwable th) {
        log.error(th.getMessage(),th);
        return handleExeption(TRY_AGAIN_IMG_PATH, th);
    }

    private ModelAndView handleExeption(String imgPath, Throwable th){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(MODEL_ATTR_NAME, imgPath);
        modelAndView.addObject(EXCEPTION_MESSAGE_ATTR_NAME, "Internal server error");
        return view(NOT_FOUND_VIEW, modelAndView);
    }
}

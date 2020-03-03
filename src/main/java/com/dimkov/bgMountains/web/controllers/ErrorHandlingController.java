package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.web.annotations.PageTitle;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandlingController extends BaseController implements ErrorController {
    private static final String NOT_FOUND_IMG_PATH = "/images/ic_notfound.png";
    private static final String NOT_FOUND_VIEW = "errorPages/errorPage";
    private static final String ERROR_PATH = "/error";


    @GetMapping("/error")
    @PageTitle("Error occurred")
    public ModelAndView handleError(HttpServletRequest httpServletRequest, ModelAndView modelAndView) {
        modelAndView.addObject(Constants.MODEL_ATTR_NAME, NOT_FOUND_IMG_PATH);
        return view(NOT_FOUND_VIEW, modelAndView);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}

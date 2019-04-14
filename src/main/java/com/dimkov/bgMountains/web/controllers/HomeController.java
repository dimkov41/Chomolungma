package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.web.annotations.PageTitle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController extends BaseController {
    @GetMapping("/")
    @PageTitle("home")
    public ModelAndView index(){
        return view("index");
    }
}

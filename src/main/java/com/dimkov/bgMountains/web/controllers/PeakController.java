package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.service.PeakService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/peaks")
public class PeakController extends BaseController {
    private final PeakService peakService;

    @Autowired
    public PeakController(PeakService peakService) {
        this.peakService = peakService;
    }


    @GetMapping
    public ModelAndView showPeakHome(ModelAndView modelAndView){
        
    }
}

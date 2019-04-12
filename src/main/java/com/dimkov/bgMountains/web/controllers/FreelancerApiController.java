package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.models.binding.FreelancerRegisterBindingModel;
import com.dimkov.bgMountains.service.FreelancerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RequestMapping("/mountainguides")
public class FreelancerApiController extends BaseController {
    private final FreelancerService freelancerService;
    private final ModelMapper modelMapper;

    @Autowired
    public FreelancerApiController(FreelancerService freelancerService, ModelMapper modelMapper) {
        this.freelancerService = freelancerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/check/{id}")
    public void checkIfFree(
            @PathVariable("id") String id,
            @ModelAttribute FreelancerRegisterBindingModel freelancerRegisterBindingModel
            ) throws ParseException {
        this.freelancerService.checkIfAvailable("2019-04-05", "2019-04-11");
    }
}

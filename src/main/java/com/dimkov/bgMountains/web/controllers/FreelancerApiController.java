package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.models.binding.FreelancerRegisterBindingModel;
import com.dimkov.bgMountains.service.FreelancerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@Controller
@RequestMapping("/mountainguides")
public class FreelancerApiController extends BaseController {
    private final static String START_DATE_PARAM_NAME = "startDate";
    private final static String END_DATE_PARAM_NAME = "endDate";

    private final FreelancerService freelancerService;

    @Autowired
    public FreelancerApiController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @PostMapping("/check/{id}")
    @ResponseBody
    public boolean checkIfFree(
            @PathVariable("id") String id,
            HttpServletRequest request,
            @ModelAttribute FreelancerRegisterBindingModel freelancerRegisterBindingModel
    ) throws ParseException {

        String startDate = request.getParameter(START_DATE_PARAM_NAME);
        String endDate = request.getParameter(END_DATE_PARAM_NAME);

        return this.freelancerService.checkIfAvailable(startDate, endDate, id);
    }


}

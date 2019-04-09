package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.UserFreelancerRegisterBindingModel;
import com.dimkov.bgMountains.service.FreelancerService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
public class FreelancerController extends BaseController {
    private static final String BECOME_FREELANCER_VIEW = "freelancer/become-freelancer";

    private static final String BECOME_FREELANCER_ERROR_PATH = "/becomeFreelancer?error=true";

    private final FreelancerService freelancerService;
    private final ModelMapper modelMapper;

    public FreelancerController(FreelancerService freelancerService, ModelMapper modelMapper) {
        this.freelancerService = freelancerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView showBecomeFreelancerForm(){
        return view(BECOME_FREELANCER_VIEW);
    }

    @PostMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView registerFreelancer(@Valid @ModelAttribute UserFreelancerRegisterBindingModel userFreelancerRegisterBindingModel,
                                           Errors errors,
                                           Principal principal) throws IOException {
        if(errors.hasErrors()){
            return redirect(BECOME_FREELANCER_ERROR_PATH);
        }

        FreelancerRegisterServiceModel freelancerRegisterServiceModel =
                this.modelMapper.map(userFreelancerRegisterBindingModel, FreelancerRegisterServiceModel.class);

        this.freelancerService.save(freelancerRegisterServiceModel, principal.getName());

        return view(BECOME_FREELANCER_VIEW);
    }
}

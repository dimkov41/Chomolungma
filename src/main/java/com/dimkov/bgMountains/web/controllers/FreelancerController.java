package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.FreelancerRegisterBindingModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.service.FreelancerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FreelancerController extends BaseController{
    private static final String BECOME_FREELANCER_ERROR_PATH = "becomeFreelancer?error=true";

    private static final String BECOME_FREELANCER_VIEW = "freelancer/become-freelancer";

    private static final String FREELANCERS_PATH = "/freelancers";


    private final ModelMapper modelMapper;
    private final FreelancerService freelancerService;

    @Autowired
    public FreelancerController(ModelMapper modelMapper, FreelancerService freelancerService) {
        this.modelMapper = modelMapper;
        this.freelancerService = freelancerService;
    }

    @GetMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView showBecomeFreelancerForm() {
        return view(BECOME_FREELANCER_VIEW);
    }

    @PostMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView registerFreelancer(
            @Valid @ModelAttribute FreelancerRegisterBindingModel freelancerRegisterBindingModel,
            Errors errors,
            Principal principal) throws IOException {
        if (errors.hasErrors()) {
            return redirect(BECOME_FREELANCER_ERROR_PATH);
        }

        FreelancerServiceModel freelancerServiceModel =
                this.modelMapper.map(freelancerRegisterBindingModel, FreelancerServiceModel.class);

        if (!this.freelancerService.register(freelancerServiceModel, principal.getName())) {
            return redirect(BECOME_FREELANCER_ERROR_PATH);
        }

        return redirect(FREELANCERS_PATH);
    }
}

package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.UserFreelancerRegisterBindingModel;
import com.dimkov.bgMountains.domain.models.service.UserFreelancerRegisterServiceModel;
import org.modelmapper.ModelMapper;
import com.dimkov.bgMountains.domain.models.binding.UserRegisterBindingModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    private static final String BECOME_FREELANCER_ERROR_PATH = "/users/becomeFreelancer?error=true";

    private static final String REGISTER_VIEW = "register";
    private static final String LOGIN_VIEW = "login";
    private static final String BECOME_FREELANCER_VIEW = "freelancer/become-freelancer";

    private static final String USERS_REGISTER_PATH = "/users/register";
    private static final String USERS_LOGIN_PATH = "/users/login";
    private static final String FREELANCERS_PATH = "/users/freelancers";

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return view(REGISTER_VIEW);
    }

    @PostMapping("/register")
    public ModelAndView registerPost(@Valid @ModelAttribute UserRegisterBindingModel userRegisterBindingModel, Errors errors) {
        if (errors.hasErrors() ||
                !userRegisterBindingModel.getConfirmPassword().equals(userRegisterBindingModel.getPassword())) {
            return redirect(USERS_REGISTER_PATH);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        boolean isRegistered = this.userService.register(userServiceModel);

        if (!isRegistered) {
            return redirect(USERS_REGISTER_PATH);
        }

        return redirect(USERS_LOGIN_PATH);
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return view(LOGIN_VIEW);
    }


    @GetMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView showBecomeFreelancerForm() {
        return view(BECOME_FREELANCER_VIEW);
    }

    @PostMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView registerFreelancer(
            @Valid @ModelAttribute UserFreelancerRegisterBindingModel userFreelancerRegisterBindingModel,
                                           Errors errors,
                                           Principal principal) throws IOException {
        if (errors.hasErrors()) {
            return redirect(BECOME_FREELANCER_ERROR_PATH);
        }

        UserFreelancerRegisterServiceModel userFreelancerRegisterServiceModel =
                this.modelMapper.map(userFreelancerRegisterBindingModel, UserFreelancerRegisterServiceModel.class);

        if(!this.userService.registerFreelancer(userFreelancerRegisterServiceModel, principal.getName())) {
            return redirect(BECOME_FREELANCER_ERROR_PATH);
        }

        return redirect(FREELANCERS_PATH);
    }
}

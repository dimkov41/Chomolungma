package com.dimkov.bgMountains.web.controllers;

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

@Controller
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    private static final String REGISTER_VIEW = "register";
    private static final String LOGIN_VIEW = "login";

    private static final String USERS_REGISTER_PATH = "/users/register";
    private static final String USERS_LOGIN_PATH = "/users/login";

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

        if(!isRegistered){
            return redirect(USERS_REGISTER_PATH);
        }

        return redirect(USERS_LOGIN_PATH);
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage(){
        return view(LOGIN_VIEW);
    }
}

package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.UserChangeBindingModel;
import com.dimkov.bgMountains.domain.models.service.UserChangeServiceModel;
import com.dimkov.bgMountains.domain.models.view.UserViewModel;
import com.dimkov.bgMountains.util.Constants;
import org.modelmapper.ModelMapper;
import com.dimkov.bgMountains.domain.models.binding.UserRegisterBindingModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.CciOperationNotSupportedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    private static final String REGISTER_VIEW = "register";
    private static final String LOGIN_VIEW = "login";
    private static final String USER_PROFILE_VIEW = "profile";

    private static final String USERS_REGISTER_PATH = "/users/register";
    private static final String USERS_LOGIN_PATH = "/users/login";
    private static final String USERS_PROFILE_ERROR_PATH = "/users/profile?error=true";
    private static final String HOME_PATH = "/";

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

    @GetMapping("/profile")
    public ModelAndView showProfile(
            ModelAndView modelAndView,
            Principal principal){
        UserServiceModel userServiceModel = this.userService.findByUsername(principal.getName())
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, this.modelMapper.map(userServiceModel, UserViewModel.class));

        return view(USER_PROFILE_VIEW, modelAndView);
    }

    @PostMapping("/profile")
    public ModelAndView makeChanges(
            @ModelAttribute @Valid UserChangeBindingModel userChangeBindingModel,
            Principal principal,
            Errors errors){
        if(errors.hasErrors()){
            return redirect(USERS_PROFILE_ERROR_PATH);
        }

        UserChangeServiceModel changeServiceModel =
                this.modelMapper.map(userChangeBindingModel, UserChangeServiceModel.class);
        changeServiceModel.setUsername(principal.getName());

        if(!this.userService.changePassword(changeServiceModel)){
            return redirect(USERS_PROFILE_ERROR_PATH);
        }

        return redirect(HOME_PATH);
    }

}

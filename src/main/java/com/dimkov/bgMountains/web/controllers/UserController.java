package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.entities.Role;
import com.dimkov.bgMountains.domain.models.binding.UserChangeBindingModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserChangeServiceModel;
import com.dimkov.bgMountains.domain.models.view.FreelancerViewModel;
import com.dimkov.bgMountains.domain.models.view.UserViewModel;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.web.annotations.PageTitle;
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
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    private static final String REGISTER_VIEW = "register";
    private static final String LOGIN_VIEW = "login";
    private static final String USER_PROFILE_VIEW = "profile";
    private static final String ADMIN_VIEW = "admin";

    private static final String USERS_REGISTER_PATH = "/users/register";
    private static final String USERS_LOGIN_PATH = "/users/login";
    private static final String USERS_PROFILE_ERROR_PATH = "/users/profile?error=true";
    private static final String HOME_PATH = "/";
    private static final String ADMIN_PATH = "/users/admin";
    private static final String ADMIN_ERROR_PATH = "/users/admin?error=true";

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/register")
    @PageTitle("Register")
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
    @PageTitle("Login")
    public ModelAndView showLoginPage() {
        return view(LOGIN_VIEW);
    }

    @GetMapping("/profile")
    @PageTitle("Profile")
    public ModelAndView showProfile(
            ModelAndView modelAndView,
            Principal principal) {
        UserServiceModel userServiceModel = this.userService.findByUsername(principal.getName())
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, this.modelMapper.map(userServiceModel, UserViewModel.class));

        return view(USER_PROFILE_VIEW, modelAndView);
    }

    @PostMapping("/profile")
    public ModelAndView makeChanges(
            @ModelAttribute @Valid UserChangeBindingModel userChangeBindingModel,
            Principal principal,
            Errors errors) {
        if (errors.hasErrors()) {
            return redirect(USERS_PROFILE_ERROR_PATH);
        }
        UserChangeServiceModel changeServiceModel =
                this.modelMapper.map(userChangeBindingModel, UserChangeServiceModel.class);
        changeServiceModel.setUsername(principal.getName());
        if (!this.userService.changePassword(changeServiceModel)) {
            return redirect(USERS_PROFILE_ERROR_PATH);
        }
        return redirect(HOME_PATH);
    }

    @GetMapping("/hiredmountainguides")
    @PageTitle("Hired mountain guides")
    public ModelAndView showHiredGuides(
            ModelAndView modelAndView,
            Principal principal) {

        Set<FreelancerServiceModel> freelancerServiceModelSet =
                this.userService.getHiredFreelancers(principal.getName());

        Set<FreelancerViewModel> freelancerViewModels =
                freelancerServiceModelSet
                        .stream()
                        .map(f -> this.modelMapper.map(f, FreelancerViewModel.class))
                        .collect(Collectors.toSet());

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, freelancerViewModels);

        return view("hired-freelancers", modelAndView);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole(T(com.dimkov.bgMountains.util.Constants).ROLE_ADMIN)")
    @PageTitle("Admin panel")
    public ModelAndView showAdminPage(ModelAndView modelAndView) {
        List<UserServiceModel> userServiceModels = this.userService.findAll();

        List<UserViewModel> users =
                userServiceModels
                        .stream()
                        .map(u -> {
                            UserViewModel v = this.modelMapper.map(u, UserViewModel.class);
                            Set<String> authorities = u.getAuthorities()
                                    .stream()
                                    .map(Role::getAuthority)
                                    .collect(Collectors.toSet());

                            v.setAuthorities(authorities);
                            return v;
                        })
                        .collect(Collectors.toList());

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, users);

        return view(ADMIN_VIEW, modelAndView);
    }

    @GetMapping("/setAuth/{role}/{id}")
    @PreAuthorize("hasRole(T(com.dimkov.bgMountains.util.Constants).ROLE_ADMIN)")
    @PageTitle("Set role")
    public ModelAndView setRole(
            @PathVariable("role") String role,
            @PathVariable("id") String id
    ) {
        if (!this.userService.setUserAuthorities(role, id)) {
            return redirect(ADMIN_ERROR_PATH);
        }

        return redirect(ADMIN_PATH);
    }
}

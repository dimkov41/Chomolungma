package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/users")
public class UserApiController{
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/checkUsername/{username}")
    @ResponseBody
    public boolean checkUsername(@PathVariable String username, Principal principal){
        return this.userService.findByUsername(username).isPresent();
    }

    @GetMapping("/checkEmail/{email}")
    @ResponseBody
    public boolean checkEmail(@PathVariable String email){
        return this.userService.findByEmail(email).isPresent();
    }
}

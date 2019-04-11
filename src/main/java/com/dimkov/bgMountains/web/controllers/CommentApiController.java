package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.service.CommentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class CommentApiController {
    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/{id}")
    public boolean registerComment(@PathVariable String freelancerId,
                                   Principal principal,
                                   @RequestBody String comment){

        String currentUsername = principal.getName();

        return this.commentService.saveComment(comment, currentUsername, freelancerId);
    }
}

package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.CommentBindingModel;
import com.dimkov.bgMountains.domain.models.service.CommentAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.CommentServiceModel;
import com.dimkov.bgMountains.service.CommentService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentApiController {
    private final static String COMMENT_PARAM_NAME = "comment";
    private final static String DATE_PARAM_NAME = "currentDate";

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentApiController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/{id}")
    public boolean registerComment(
            @PathVariable String id,
            @RequestParam(COMMENT_PARAM_NAME) String comment,
            @RequestParam(DATE_PARAM_NAME) String date) {
        // Obtain the current logged-in user
        Authentication
                authentication = SecurityContextHolder.getContext().getAuthentication();
        String userCreated = authentication.getName();
        CommentBindingModel commentBindingModel = new CommentBindingModel(comment, date, id, userCreated);
        CommentAddServiceModel commentAddServiceModel = this.modelMapper.map(commentBindingModel, CommentAddServiceModel.class);
        return this.commentService.saveComment(commentAddServiceModel);
    }

    @GetMapping(value = "/show/{id}")
    public String showPaginatedPeakHome(@PathVariable("id") String id) {
        List<CommentServiceModel> comments = this.commentService.findAll(id);
        return new Gson().toJson(comments);
    }
}

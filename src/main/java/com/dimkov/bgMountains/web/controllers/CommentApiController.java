package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.CommentBindingModel;
import com.dimkov.bgMountains.domain.models.service.CommentAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.CommentServiceModel;
import com.dimkov.bgMountains.service.CommentService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            HttpServletRequest request,
            @ModelAttribute CommentBindingModel commentBindingModel) throws IOException {
        String comment = request.getParameter(COMMENT_PARAM_NAME);
        String date = request.getParameter(DATE_PARAM_NAME);
        commentBindingModel = new CommentBindingModel(comment, date, id);
        return this.commentService.saveComment(this.modelMapper.map(commentBindingModel, CommentAddServiceModel.class));
    }

    @GetMapping(value = "/show/{id}")
    public String showPaginatedPeakHome(@PathVariable("id") String id) {
        List<CommentServiceModel> comments = this.commentService.findAll(id);
        return new Gson().toJson(comments);
    }
}

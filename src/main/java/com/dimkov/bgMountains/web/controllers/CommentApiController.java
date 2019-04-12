package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.CommentBindingModel;
import com.dimkov.bgMountains.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentApiController {
    private final static String COMMENT_PARAM_NAME = "comment";
    private final static String DATE_PARAM_NAME = "";

    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}")
    public boolean registerComment(
            @PathVariable String id,
            HttpServletRequest request,
            @ModelAttribute CommentBindingModel commentBindingModelommentBindingModel) throws IOException {

        String comment = request.getParameter(COMMENT_PARAM_NAME);

//        return this.commentService.saveComment(comment, id);
        return true;
    }

//    @GetMapping(value = "/{page}")
//    public ModelAndView showPaginatedPeakHome(
//            @PathVariable("page") int page,
//            ModelAndView modelAndView) {
//
//        Page<PeakServiceModel> peakPage = this.peakService.findPaginated(page);
//        int pageCount = peakPage.getTotalPages();
//
//        if (pageCount > Constants.ZERO) {
//            List<Integer> pageNumbers =
//                    IntStream.rangeClosed(1, pageCount)
//                            .boxed()
//                            .collect(Collectors.toList());
//
//            modelAndView.addObject(Constants.PAGES_ATTR_NAME, pageNumbers);
//        }
//
//        List<PeakViewModel> peaks =
//                peakPage
//                        .map(p -> this.modelMapper.map(p, PeakViewModel.class))
//                        .getContent();
//
//        modelAndView.addObject(Constants.MODEL_ATTR_NAME, peaks);
//
//        return view(PEAKS_VIEW, modelAndView);
//    }
}

package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.CommentBindingModel;
import com.dimkov.bgMountains.domain.models.service.CommentServiceModel;
import com.dimkov.bgMountains.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
            @ModelAttribute CommentBindingModel commentBindingModelommentBindingModel) throws IOException {

        String comment = request.getParameter(COMMENT_PARAM_NAME);
        String date = request.getParameter(DATE_PARAM_NAME);

        CommentBindingModel commentBindingModel = new CommentBindingModel(comment, date, id);

//        return this.commentService.saveComment(this.modelMapper.map(commentBindingModel, CommentServiceModel.class));
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

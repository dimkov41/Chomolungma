package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.view.FreelancerViewModel;
import com.dimkov.bgMountains.domain.models.view.PeakViewModel;
import com.dimkov.bgMountains.service.FreelancerService;
import com.dimkov.bgMountains.service.PeakService;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController extends BaseController {
    private static final int FIRST_PAGE = 1;
    private static final int MAX_FREELANCERS_PER_PAGE = 4;
    private static final int MAX_PEAK_PER_PAGE = 2;

    private static final String VIEW_NAME = "index";


    private final FreelancerService freelancerService;
    private final PeakService peakService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(FreelancerService freelancerService, PeakService peakService, ModelMapper modelMapper) {
        this.freelancerService = freelancerService;
        this.peakService = peakService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @PageTitle("Home")
    public ModelAndView index(ModelAndView modelAndView) {
        List<FreelancerViewModel> freelancers =
                this.freelancerService
                        .findPaginated(FIRST_PAGE, MAX_FREELANCERS_PER_PAGE)
                        .map(f -> this.modelMapper.map(f, FreelancerViewModel.class))
                        .getContent();

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, freelancers);

        List<PeakViewModel> peaks =
                this.peakService.findPaginated(FIRST_PAGE, MAX_PEAK_PER_PAGE)
                        .map(p -> this.modelMapper.map(p, PeakViewModel.class))
                        .map(p -> {
                            if( p.getDescription()!=null && p.getDescription().length()>80 ){
                                p.setDescription(p.getDescription().substring(0,80) + "...");
                            }
                            return p;
                        })
                        .getContent();
        modelAndView.addObject(Constants.PEAKS_ATTR_NAME, peaks);
        return view(VIEW_NAME, modelAndView);
    }
}

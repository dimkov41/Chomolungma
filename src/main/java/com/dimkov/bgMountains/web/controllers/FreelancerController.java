package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.FreelancerHireBindingModel;
import com.dimkov.bgMountains.domain.models.binding.FreelancerRegisterBindingModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerHireServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.view.FreelancerViewModel;
import com.dimkov.bgMountains.service.FreelancerService;
import com.dimkov.bgMountains.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class FreelancerController extends BaseController {
    private static final String BECOME_FREELANCER_ERROR_PATH = "becomeFreelancer?error=true";

    private static final String BECOME_FREELANCER_VIEW = "freelancer/become-freelancer";
    private static final String FREELANCER_VIEW = "freelancer/all-freelancers";
    private static final String FREELANCER_DETAILS_VIEW = "freelancer/freelancer-details";
    private static final String SUCCESSFULL_HIRED_FREELANCER_VIEW  = "freelancer/successful-hired-freelancer";


    private static final String FREELANCERS_PATH = "/freelancers";


    private final ModelMapper modelMapper;
    private final FreelancerService freelancerService;

    @Autowired
    public FreelancerController(ModelMapper modelMapper, FreelancerService freelancerService) {
        this.modelMapper = modelMapper;
        this.freelancerService = freelancerService;
    }

    @GetMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView showBecomeFreelancerForm() {
        return view(BECOME_FREELANCER_VIEW);
    }

    @PostMapping("/becomeFreelancer")
    @PreAuthorize("!hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_FREELANCER)")
    public ModelAndView registerFreelancer(
            @Valid @ModelAttribute FreelancerRegisterBindingModel freelancerRegisterBindingModel,
            Errors errors,
            Principal principal) throws IOException {
        if (errors.hasErrors()) {
            return redirect(BECOME_FREELANCER_ERROR_PATH);
        }

        FreelancerAddServiceModel freelancerAddServiceModel =
                this.modelMapper.map(freelancerRegisterBindingModel, FreelancerAddServiceModel.class);

        if (!this.freelancerService.register(freelancerAddServiceModel, principal.getName())) {
            return redirect(BECOME_FREELANCER_ERROR_PATH);
        }

        return redirect(FREELANCERS_PATH);
    }


    @SuppressWarnings("Duplicates")
    @GetMapping("/mountainguides/{page}")
    public ModelAndView showAllMountainGuides(
            ModelAndView modelAndView,
            @PathVariable("page") int page) {

        Page<FreelancerServiceModel> freelancerPage = this.freelancerService.findPaginated(page);
        int pageCount = freelancerPage.getTotalPages();

        if (pageCount > Constants.ZERO) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, pageCount)
                            .boxed()
                            .collect(Collectors.toList());

            modelAndView.addObject(Constants.PAGES_ATTR_NAME, pageNumbers);
        }

        List<FreelancerViewModel> freelancers =
                freelancerPage
                        .map(p -> this.modelMapper.map(p, FreelancerViewModel.class))
                        .getContent();

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, freelancers);

        return view(FREELANCER_VIEW, modelAndView);
    }


    @GetMapping("/mountainguides/details/{id}")
    public ModelAndView showFreelancerDetails(@PathVariable String id, ModelAndView modelAndView) {
        FreelancerServiceModel foundFreelancer = this.freelancerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constants.FREELANCER_NOT_FOUND_MESSAGE));

        modelAndView.addObject(
                Constants.MODEL_ATTR_NAME,
                this.modelMapper.map(foundFreelancer, FreelancerViewModel.class)
        );

        return view(FREELANCER_DETAILS_VIEW, modelAndView);
    }

    @PostMapping("/hire/{id}")
    public ModelAndView hireFreelancer(
            @PathVariable("id") String id,
            @ModelAttribute FreelancerHireBindingModel freelancerHireBindingModel,
            Principal principal
    ){
        freelancerHireBindingModel.setId(id);
        FreelancerHireServiceModel freelancerHireServiceModel =
                this.modelMapper.map(freelancerHireBindingModel, FreelancerHireServiceModel.class);
//        this.freelancerService.hireFreelancer(freelancerHireServiceModel, principal.getName());

        return view(SUCCESSFULL_HIRED_FREELANCER_VIEW);
    }
}

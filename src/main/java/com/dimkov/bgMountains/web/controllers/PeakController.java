package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.PeakAddBindingModel;
import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import com.dimkov.bgMountains.domain.models.view.MountainViewModel;
import com.dimkov.bgMountains.domain.models.view.PeakRedirectViewModel;
import com.dimkov.bgMountains.domain.models.view.PeakViewModel;
import com.dimkov.bgMountains.service.MountainService;
import com.dimkov.bgMountains.service.PeakService;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/peaks")
public class PeakController extends BaseController {
    private static final String ALL_PEAKS_PATH = "/peaks";
    private static final String ADD_PEAK_ERROR_PATH = "/peaks/add?error=true";

    private static final String PEAKS_VIEW = "peak/peaks-home";
    private static final String ADD_PEAK_VIEW = "peak/peak-add";
    private static final String PEAK_DETAILS_VIEW = "peak/peak-details";

    private static final int MAX_ELEMENTS_PER_PAGE = 8;

    private final PeakService peakService;
    private final MountainService mountainService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeakController(PeakService peakService,
                          MountainService mountainService,
                          ModelMapper modelMapper) {
        this.peakService = peakService;
        this.mountainService = mountainService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(value = "/{page}")
    @PageTitle("Peaks")
    public ModelAndView showPaginatedPeakHome(
            @PathVariable("page") int page,
            ModelAndView modelAndView) {
        return this.findPaginated(page, null, modelAndView);
    }

    @GetMapping("/add")
    @PageTitle("Add peak")
    public ModelAndView showAddForm(ModelAndView modelAndView, Model model) {
        List<MountainViewModel> mountains =
                this.mountainService.findAll()
                        .stream()
                        .map(m -> this.modelMapper.map(m, MountainViewModel.class))
                        .collect(Collectors.toList());

        PeakRedirectViewModel peakRedirectViewModel = new PeakRedirectViewModel();
        if (model.containsAttribute(Constants.MODEL_ATTR_NAME)) {
            peakRedirectViewModel = (PeakRedirectViewModel) model.asMap().get(Constants.MODEL_ATTR_NAME);
        }

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, peakRedirectViewModel);
        modelAndView.addObject(Constants.MOUNTAINS_ATTR_NAME, mountains);

        return view(ADD_PEAK_VIEW, modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addPeak(@Valid @ModelAttribute PeakAddBindingModel peakAddBindingModel,
                                RedirectAttributes redirectAttributes,
                                Errors errors,
                                Principal principal) throws IOException {
        if (errors.hasErrors()) {
            return redirect(ADD_PEAK_ERROR_PATH);
        }

        PeakAddServiceModel peakAddServiceModel = this.modelMapper.map(peakAddBindingModel, PeakAddServiceModel.class);

        if (!this.peakService.save(peakAddServiceModel, principal.getName())) {

            PeakRedirectViewModel peakRedirectViewModel = this.modelMapper.map(peakAddBindingModel, PeakRedirectViewModel.class);
            redirectAttributes.addFlashAttribute(Constants.MODEL_ATTR_NAME, peakRedirectViewModel);
            return redirect(ADD_PEAK_ERROR_PATH);
        }


        return redirect(ALL_PEAKS_PATH);
    }

    @GetMapping(value = "/{mountainId}/{page}")
    @PageTitle("Peaks")
    public ModelAndView showPeaksForMountain(
            @PathVariable("page") int page,
            @PathVariable String mountainId,
            ModelAndView modelAndView) {
        return this.findPaginated(page, mountainId, modelAndView);
    }

    @GetMapping("/details/{id}")
    public ModelAndView showPeakDetails(
            @PathVariable String id,
            ModelAndView modelAndView) {
        PeakServiceModel peakServiceModel =
                this.peakService.findById(id)
                        .orElseThrow(() -> new NoSuchElementException(Constants.PEAK_NOT_FOUND_MESSAGE));

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, this.modelMapper.map(peakServiceModel, PeakViewModel.class));

        return view(PEAK_DETAILS_VIEW, modelAndView);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(T(com.dimkov.bgMountains.util.Constants).ROLE_MODERATOR)")
    @PageTitle("Peak - delete")
    public ModelAndView deletePeak(
            @PathVariable("id") String id
    ) {
        this.peakService.deletePeak(id);

        return redirect(ALL_PEAKS_PATH);
    }

    @SuppressWarnings("Duplicates")
    private ModelAndView findPaginated(int page, String mountainId, ModelAndView modelAndView) {
        Page<PeakServiceModel> peakPage = this.peakService.findPaginated(page, MAX_ELEMENTS_PER_PAGE);

        if (mountainId != null) {
            peakPage = this.peakService.findPaginated(page, MAX_ELEMENTS_PER_PAGE, mountainId);
        }

        int pageCount = peakPage.getTotalPages();

        if (pageCount > Constants.ZERO) {
            List<Integer> pageNumbers =
                    IntStream.rangeClosed(1, pageCount)
                            .boxed()
                            .collect(Collectors.toList());

            modelAndView.addObject(Constants.PAGES_ATTR_NAME, pageNumbers);
        }

        List<PeakViewModel> peaks =
                peakPage
                        .map(p -> this.modelMapper.map(p, PeakViewModel.class))
                        .map(p -> {
                            String desc = p.getDescription();
                            if (desc.length() > 101) {
                                p.setDescription(desc.substring(0, 101) + "...");
                            }
                            return p;
                        })
                        .getContent();

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, peaks);

        return view(PEAKS_VIEW, modelAndView);
    }
}

package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.PeakAddBindingModel;
import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import com.dimkov.bgMountains.domain.models.view.MountainViewModel;
import com.dimkov.bgMountains.domain.models.view.PeakRedirectViewModel;
import com.dimkov.bgMountains.domain.models.view.PeakViewModel;
import com.dimkov.bgMountains.service.CloudinaryService;
import com.dimkov.bgMountains.service.MountainService;
import com.dimkov.bgMountains.service.PeakService;
import com.dimkov.bgMountains.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/peaks")
public class PeakController extends BaseController {
    private static final String ALL_PEAKS_PATH = "/peaks";
    private static final String ADD_PEAK_PATH = "/peaks/add";
    private static final String ADD_PEAK_ERROR_PATH = "/peaks/add?error=true";

    private static final String PEAKS_VIEW = "peak/peaks-home";
    private static final String ADD_PEAK_VIEW = "peak/peak-add";

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


    @GetMapping
    public ModelAndView showPeakHome(ModelAndView modelAndView) {
        List<PeakViewModel> peaks =
                this.peakService.findAll()
                        .stream()
                        .map(m -> this.modelMapper.map(m, PeakViewModel.class))
                        .collect(Collectors.toList());

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, peaks);

        return view(PEAKS_VIEW, modelAndView);
    }

    @GetMapping("/add")
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
        modelAndView.addObject(Constants.MOUNTAINS_ATTR_NAME_FOR_ADD_PEAK_VIEW, mountains);

        return view(ADD_PEAK_VIEW, modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addPeak(@Valid @ModelAttribute PeakAddBindingModel peakAddBindingModel,
                                RedirectAttributes redirectAttributes,
                                Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return redirect(ADD_PEAK_ERROR_PATH);
        }

        PeakAddServiceModel peakAddServiceModel = this.modelMapper.map(peakAddBindingModel, PeakAddServiceModel.class);

        if (!this.peakService.save(peakAddServiceModel)) {
            PeakRedirectViewModel peakRedirectViewModel = this.modelMapper.map(peakAddBindingModel, PeakRedirectViewModel.class);
            redirectAttributes.addFlashAttribute(Constants.MODEL_ATTR_NAME, peakRedirectViewModel);
            return redirect(ADD_PEAK_ERROR_PATH);
        }


        return redirect(ALL_PEAKS_PATH);
    }
}

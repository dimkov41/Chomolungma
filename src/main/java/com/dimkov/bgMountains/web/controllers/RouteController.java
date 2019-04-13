package com.dimkov.bgMountains.web.controllers;

import com.dimkov.bgMountains.domain.models.binding.RouteAddBindingModel;
import com.dimkov.bgMountains.domain.models.service.RouteAddSeviceModel;
import com.dimkov.bgMountains.domain.models.service.RouteServiceModel;
import com.dimkov.bgMountains.domain.models.view.MountainViewModel;
import com.dimkov.bgMountains.domain.models.view.RouteRedirectViewModel;
import com.dimkov.bgMountains.domain.models.view.RouteViewModel;
import com.dimkov.bgMountains.service.MountainService;
import com.dimkov.bgMountains.service.RouteService;
import com.dimkov.bgMountains.util.Constants;
import org.modelmapper.ModelMapper;
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
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/routes")
public class RouteController extends BaseController{
    private static final String ADD_ROUTE_VIEW = "route/route-add";
    private static final String ALL_ROUTES_VIEW = "route/all-routes";

    private static final String ADD_ROUTE_ERROR_PATH = "/routes/add?error=true";
    private static final String ALL_ROUTES_PATH = "/routes";

    private final ModelMapper modelMapper;
    private final RouteService routeService;
    private final MountainService mountainService;

    public RouteController(ModelMapper modelMapper, RouteService routeService, MountainService mountainService) {
        this.modelMapper = modelMapper;
        this.routeService = routeService;
        this.mountainService = mountainService;
    }

    @GetMapping("/add")
    public ModelAndView showAddForm(ModelAndView modelAndView, Model model) {
        List<MountainViewModel> mountains =
                this.mountainService.findAll()
                        .stream()
                        .map(m -> this.modelMapper.map(m, MountainViewModel.class))
                        .collect(Collectors.toList());

        RouteRedirectViewModel routeRedirectViewModel = new RouteRedirectViewModel();
        if (model.containsAttribute(Constants.MODEL_ATTR_NAME)) {
            routeRedirectViewModel = (RouteRedirectViewModel) model.asMap().get(Constants.MODEL_ATTR_NAME);
        }

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, routeRedirectViewModel);
        modelAndView.addObject(Constants.MOUNTAINS_ATTR_NAME, mountains);

        return view(ADD_ROUTE_VIEW, modelAndView);
    }


    @PostMapping("/add")
    public ModelAndView addPeak(@Valid @ModelAttribute RouteAddBindingModel routeAddBindingModel,
                                RedirectAttributes redirectAttributes,
                                Errors errors,
                                Principal principal) throws IOException {
        RouteRedirectViewModel routeRedirectViewModel =
                this.modelMapper.map(routeAddBindingModel, RouteRedirectViewModel.class);

        routeAddBindingModel.setUsername(principal.getName());
        if (errors.hasErrors() ||
                !this.routeService.save(
                        this.modelMapper.map(routeAddBindingModel, RouteAddSeviceModel.class)
                )) {
            redirectAttributes.addFlashAttribute(Constants.MODEL_ATTR_NAME, routeRedirectViewModel);
            return redirect(ADD_ROUTE_ERROR_PATH);
        }

        return redirect(ALL_ROUTES_PATH);
    }

    @GetMapping()
    public ModelAndView showAll(ModelAndView modelAndView){
        List<RouteServiceModel> routeServiceModels = this.routeService.findAll();

        List<RouteViewModel> routeViewModels =
                routeServiceModels
                .stream()
                .map(r -> this.modelMapper.map(r, RouteViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(Constants.MODEL_ATTR_NAME, routeViewModels);

        return view(ALL_ROUTES_VIEW, modelAndView);
    }
}

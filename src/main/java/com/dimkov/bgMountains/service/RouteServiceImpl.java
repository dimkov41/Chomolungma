package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Mountain;
import com.dimkov.bgMountains.domain.entities.Route;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.MountainServiceModel;
import com.dimkov.bgMountains.domain.models.service.RouteAddSeviceModel;
import com.dimkov.bgMountains.domain.models.service.RouteServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.RouteRepository;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.validation.RouteValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    private static final String ROUTE_NOT_VALID_MESSAGE = "Route not valid";

    private final ModelMapper modelMapper;
    private final RouteRepository routeRepository;
    private final RouteValidationService routeValidationService;
    private final MountainService mountainService;
    private final UserService userService;

    @Autowired
    public RouteServiceImpl(ModelMapper modelMapper, RouteRepository routeRepository, RouteValidationService routeValidationService, MountainService mountainService, UserService userService) {
        this.modelMapper = modelMapper;
        this.routeRepository = routeRepository;
        this.routeValidationService = routeValidationService;
        this.mountainService = mountainService;
        this.userService = userService;
    }

    @Override
    public List<RouteServiceModel> findAll(){
        List<Route> routes = this.routeRepository.findAll();
        return routes
                .stream()
                .map(r -> this.modelMapper.map(r, RouteServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(RouteAddSeviceModel routeAddSeviceModel) {
        if (!routeValidationService.isValid(routeAddSeviceModel)) {
            throw new IllegalArgumentException(ROUTE_NOT_VALID_MESSAGE);
        }

        Route route = this.modelMapper.map(routeAddSeviceModel, Route.class);

        MountainServiceModel mountainServiceModel =
                this.mountainService
                        .findByName(
                                routeAddSeviceModel.getMountainName()
                        )
                        .orElseThrow(() -> new NoSuchElementException(Constants.MOUNTAIN_NOT_FOUND_MESSAGE));

        route.setLocation(
                this.modelMapper.map(mountainServiceModel, Mountain.class)
        );

        UserServiceModel userServiceModel =
                this.userService.findByUsername(routeAddSeviceModel.getUsername())
                        .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        route.setAuthor(
                this.modelMapper.map(userServiceModel, User.class)
        );

        try {
            this.routeRepository.save(route);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

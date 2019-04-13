package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.RouteAddSeviceModel;
import com.dimkov.bgMountains.domain.models.service.RouteServiceModel;

import java.util.List;

public interface RouteService {
    List<RouteServiceModel> findAll();

    boolean save(RouteAddSeviceModel routeAddSeviceModel);
}

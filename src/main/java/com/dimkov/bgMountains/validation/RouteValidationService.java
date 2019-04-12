package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.RouteAddSeviceModel;

public interface RouteValidationService {
    boolean isValid(RouteAddSeviceModel routeAddSeviceModel);
}

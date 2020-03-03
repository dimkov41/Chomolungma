package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.RouteAddSeviceModel;
import org.springframework.stereotype.Component;

@Component
public class RouteValidationServiceImpl implements RouteValidationService {
    @Override
    public boolean isValid(RouteAddSeviceModel routeAddSeviceModel) {
        return routeAddSeviceModel != null;
    }
}

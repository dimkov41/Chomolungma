package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.UserChangeServiceModel;

public interface UserValidationService {
     boolean isChangeModelValid(UserChangeServiceModel userChangeServiceModel);
}

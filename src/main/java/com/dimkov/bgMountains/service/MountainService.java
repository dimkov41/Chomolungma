package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.MountainAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.MountainServiceModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MountainService {
    boolean save(MountainAddServiceModel mountainAddServiceModel) throws IOException;

    List<MountainServiceModel> findAll();

    Optional<MountainServiceModel> findById(String id);

    Optional<MountainServiceModel> findByName(String name);
}

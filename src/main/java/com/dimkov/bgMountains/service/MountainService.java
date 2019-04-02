package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Mountain;
import com.dimkov.bgMountains.domain.models.service.MountainServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MountainService {
    boolean save(MountainServiceModel mountainServiceModel);

    List<MountainServiceModel> findAll();

    Optional<MountainServiceModel> findById(String id);
}

package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Peak;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PeakService {
    Page<Peak> findPaginated(int page,int size);

    List<PeakServiceModel> findAll();


}

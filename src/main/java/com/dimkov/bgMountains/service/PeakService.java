package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Peak;
import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PeakService {
    Page<PeakServiceModel> findPaginated(int page);

    List<PeakServiceModel> findAll();

    boolean save(PeakAddServiceModel peakAddServiceModel, String authorName) throws IOException;
}

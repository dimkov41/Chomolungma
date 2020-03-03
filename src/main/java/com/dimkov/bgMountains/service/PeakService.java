package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PeakService {
    Page<PeakServiceModel> findPaginated(int page, int maxElements);

    Page<PeakServiceModel> findPaginated(int page, int maxElements, String mountainId);

    Optional<PeakServiceModel> findById(String id);

    List<PeakServiceModel> findAll();

    boolean save(PeakAddServiceModel peakAddServiceModel, String authorName) throws IOException;

    boolean deletePeak(String id);
}

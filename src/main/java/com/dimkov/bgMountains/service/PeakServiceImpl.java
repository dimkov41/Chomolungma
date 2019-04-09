package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Mountain;
import com.dimkov.bgMountains.domain.entities.Peak;
import com.dimkov.bgMountains.domain.models.service.MountainServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import com.dimkov.bgMountains.repository.PeakRepository;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.validation.PeakValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PeakServiceImpl implements PeakService {
    private static final String PEAK_VALIDATION_ERROR_MESSAGE = "Entered peak data is not correct!";

    private final PeakRepository peakRepository;
    private final MountainService mountainService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final PeakValidationService peakValidationService;

    @Autowired
    public PeakServiceImpl(PeakRepository peakRepository,
                           MountainService mountainService,
                           ModelMapper modelMapper,
                           CloudinaryService cloudinaryService,
                           PeakValidationService peakValidationService) {
        this.peakRepository = peakRepository;
        this.mountainService = mountainService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.peakValidationService = peakValidationService;
    }

    @Override
    public Page<Peak> findPaginated(int page, int size) {
        return this.peakRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<PeakServiceModel> findAll() {
        return this.peakRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PeakServiceModel.class))
                .collect(Collectors.toList());
    }


    @Override
    public boolean save(PeakAddServiceModel peakAddServiceModel) throws IOException {
        if (!peakValidationService.isValid(peakAddServiceModel)) {
            throw new IllegalArgumentException(PEAK_VALIDATION_ERROR_MESSAGE);
        }

        Peak peak = this.modelMapper.map(peakAddServiceModel, Peak.class);

        MountainServiceModel mountainServiceModel =
                this.mountainService
                        .findByName(
                                peakAddServiceModel.getMountainName()
                        )
                        .orElseThrow(() -> new NoSuchElementException(Constants.MOUNTAIN_NOT_FOUND_MESSAGE));

        peak.setLocation(this.modelMapper.map(mountainServiceModel, Mountain.class));

        peak.setImageUrl(
                this.cloudinaryService.uploadImage(peakAddServiceModel.getImage())
        );


        try {
            this.peakRepository.save(peak);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

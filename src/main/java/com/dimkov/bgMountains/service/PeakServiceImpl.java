package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Mountain;
import com.dimkov.bgMountains.domain.entities.Peak;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.MountainServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.PeakRepository;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.validation.PeakValidationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeakServiceImpl implements PeakService {
    private static final Logger log = LoggerFactory.getLogger(PeakServiceImpl.class);

    private static final String PEAK_VALIDATION_ERROR_MESSAGE = "Entered peak data is not correct!";

    private final PeakRepository peakRepository;
    private final MountainService mountainService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final PeakValidationService peakValidationService;
    private final UserService userService;

    @Autowired
    public PeakServiceImpl(PeakRepository peakRepository,
                           MountainService mountainService,
                           ModelMapper modelMapper,
                           CloudinaryService cloudinaryService,
                           PeakValidationService peakValidationService,
                           UserService userService) {
        this.peakRepository = peakRepository;
        this.mountainService = mountainService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.peakValidationService = peakValidationService;
        this.userService = userService;
    }

    @Override
    public Page<PeakServiceModel> findPaginated(int page, int maxElements) {
        Pageable pageRequest = PageRequest.of(page - Constants.ONE, maxElements);
        Page<Peak> peaks = this.peakRepository.findAll(pageRequest);

        return peaks.map(p -> this.modelMapper.map(p, PeakServiceModel.class));
    }

    @Override
    public Page<PeakServiceModel> findPaginated(int page,int maxElements, String mountainId) {
        MountainServiceModel mountainServiceModel =
                this.mountainService.findById(mountainId)
                        .orElseThrow(() -> new NoSuchElementException(Constants.MOUNTAIN_NOT_FOUND_MESSAGE));
        Mountain mountain = this.modelMapper.map(mountainServiceModel, Mountain.class);

        Pageable pageRequest = PageRequest.of(page - Constants.ONE, maxElements);
        Page<Peak> peaks = this.peakRepository.findAllByLocation(mountain, pageRequest);

        return peaks.map(p -> this.modelMapper.map(p, PeakServiceModel.class));
    }

    @Override
    public Optional<PeakServiceModel> findById(String id){
        Optional<Peak> peak = this.peakRepository.findById(id);
        return peak
                .map(p -> this.modelMapper.map(p, PeakServiceModel.class));
    }

    @Override
    public List<PeakServiceModel> findAll() {
        return this.peakRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PeakServiceModel.class))
                .collect(Collectors.toList());

    }


    @Override
    public boolean save(PeakAddServiceModel peakAddServiceModel, String authorUsername) throws IOException {
        try {
            if (!peakValidationService.isValid(peakAddServiceModel)) {
                log.error("Entered peak data is not correct!");
                throw new IllegalArgumentException(PEAK_VALIDATION_ERROR_MESSAGE);
            }
            Peak peak = this.modelMapper.map(peakAddServiceModel, Peak.class);
            log.debug("peak ----> {}", peak);
            MountainServiceModel mountainServiceModel = this.mountainService.findByName(peakAddServiceModel.getMountainName())
                            .orElseThrow(() -> new NoSuchElementException(Constants.MOUNTAIN_NOT_FOUND_MESSAGE));
            log.info("Mountain with name: {} found!", peakAddServiceModel.getMountainName());
            peak.setLocation(this.modelMapper.map(mountainServiceModel, Mountain.class));
            UserServiceModel userServiceModel = this.userService.findByUsername(authorUsername)
                            .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));
            peak.setAuthor(this.modelMapper.map(userServiceModel, User.class));
            peak.setImageUrl(this.cloudinaryService.uploadImage(peakAddServiceModel.getImage()));
            this.peakRepository.save(peak);
        } catch (Exception e) {
            log.debug("Cannot save peak with name: {} : {}", peakAddServiceModel.getName(), e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deletePeak(String id){
        try {
            this.peakRepository.deleteById(id);
        } catch (Exception e){
            return false;
        }

        return true;
    }

}

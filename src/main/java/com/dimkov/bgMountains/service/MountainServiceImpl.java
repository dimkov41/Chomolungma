package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Mountain;
import com.dimkov.bgMountains.domain.models.service.MountainAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.MountainServiceModel;
import com.dimkov.bgMountains.repository.MountainRepository;
import com.dimkov.bgMountains.validation.MountainValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MountainServiceImpl implements MountainService {
    private static final String MOUNTAIN_VALIDATION_ERROR_MESSAGE = "Entered mountain data is not correct!";

    private final ModelMapper modelMapper;
    private final MountainRepository mountainRepository;
    private final CloudinaryService cloudinaryService;
    private final MountainValidationService mountainValidationService;

    @Autowired
    public MountainServiceImpl(ModelMapper modelMapper,
                               MountainRepository mountainRepository,
                               CloudinaryService cloudinaryService,
                               MountainValidationService mountainValidationService) {
        this.modelMapper = modelMapper;
        this.mountainRepository = mountainRepository;
        this.cloudinaryService = cloudinaryService;
        this.mountainValidationService = mountainValidationService;
    }

    @Override
    public boolean save(MountainAddServiceModel mountainAddServiceModel) throws IOException {
        if (!mountainValidationService.isValid(mountainAddServiceModel)) {
            throw new IllegalArgumentException(MOUNTAIN_VALIDATION_ERROR_MESSAGE);
        }

        Mountain mountain = this.modelMapper.map(mountainAddServiceModel, Mountain.class);

        String imageUrl = this.cloudinaryService.uploadImage(mountainAddServiceModel.getImage());
        mountain.setImageUrl(imageUrl);

        try {
            this.mountainRepository.save(mountain);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public List<MountainServiceModel> findAll() {
        return this.mountainRepository.findAll()
                .stream()
                .map(m -> this.modelMapper.map(m, MountainServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MountainServiceModel> findById(String id) {
        Optional<Mountain> mountain = this.mountainRepository.findById(id);
        return mountain
                .map(m -> this.modelMapper.map(m, MountainServiceModel.class));

    }

    @Override
    public Optional<MountainServiceModel> findByName(String name) {
        Optional<Mountain> mountain = this.mountainRepository.findByName(name);
        return mountain
                .map(m -> this.modelMapper.map(m, MountainServiceModel.class));
    }


}

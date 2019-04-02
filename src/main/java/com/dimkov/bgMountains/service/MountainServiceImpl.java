package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Mountain;
import com.dimkov.bgMountains.domain.models.service.MountainServiceModel;
import com.dimkov.bgMountains.repository.MountainRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MountainServiceImpl implements MountainService {
    private final ModelMapper modelMapper;
    private final MountainRepository mountainRepository;

    @Autowired
    public MountainServiceImpl(ModelMapper modelMapper, MountainRepository mountainRepository) {
        this.modelMapper = modelMapper;
        this.mountainRepository = mountainRepository;
    }

    @Override
    public boolean save(MountainServiceModel mountainServiceModel) {
        try {
            Mountain mountain = this.modelMapper.map(mountainServiceModel,Mountain.class);
            this.mountainRepository.save(mountain);
        } catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public List<MountainServiceModel> findAll() {
        return this.mountainRepository.findAll()
                .stream()
                .map(m -> this.modelMapper.map(m,MountainServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MountainServiceModel> findById(String id) {
        Optional<Mountain> mountain = this.mountainRepository.findById(id);
        return mountain
                .map(m -> this.modelMapper.map(m, MountainServiceModel.class));

    }


}

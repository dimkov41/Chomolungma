package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Peak;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import com.dimkov.bgMountains.repository.PeakRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeakServiceImpl implements PeakService {
    private final PeakRepository peakRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PeakServiceImpl(PeakRepository peakRepository, ModelMapper modelMapper) {
        this.peakRepository = peakRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<Peak> findPaginated(int page, int size) {
        return this.peakRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public List<PeakServiceModel> findAll() {
        return this.peakRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PeakServiceModel.class))
                .collect(Collectors.toList());
    }


}

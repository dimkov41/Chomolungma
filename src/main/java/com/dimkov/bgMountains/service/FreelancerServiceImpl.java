package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.UserFreelancerRegisterServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.FreelancerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class FreelancerServiceImpl implements FreelancerService {
    private final FreelancerRepository freelancerRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public FreelancerServiceImpl(FreelancerRepository freelancerRepository,
                                 UserService userService,
                                 ModelMapper modelMapper,
                                 CloudinaryService cloudinaryService) {
        this.freelancerRepository = freelancerRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public boolean save(UserFreelancerRegisterServiceModel userFreelancerRegisterServiceModel,
                        String username) throws IOException {
        UserServiceModel userServiceModel = this.userService.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User does not exists"));



        String imageUrl = this.cloudinaryService.uploadImage(userFreelancerRegisterServiceModel.getImage());
        freelancer.setImageUrl(imageUrl);

        try {
            this.freelancerRepository.save(freelancer);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void mapFieldsToFreelancer(Freelancer freelancer, UserServiceModel userServiceModel) {
        freelancer.setId(userServiceModel.getId());
        freelancer.setEmail(userServiceModel.getEmail());
        freelancer.setPassword(userServiceModel.getPassword());
        freelancer.setUsername(userServiceModel.getUsername());
        freelancer.setAuthorities(userServiceModel.getAuthorities());
    }
}

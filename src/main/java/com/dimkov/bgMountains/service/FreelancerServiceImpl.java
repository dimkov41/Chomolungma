package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.FreelancerRepository;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.validation.FreelancerValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class FreelancerServiceImpl implements FreelancerService {
    private static final String FREELANCER_VALIDATION_ERROR_MESSAGE = "Entered data is not correct!";

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final FreelancerRepository freelancerRepository;
    private final CloudinaryService cloudinaryService;
    private final FreelancerValidationService freelancerValidationService;



    @Autowired
    public FreelancerServiceImpl(
            UserService userService,
            ModelMapper modelMapper,
            FreelancerRepository freelancerRepository,
            CloudinaryService cloudinaryService,
            FreelancerValidationService freelancerValidationService) {

        this.userService = userService;
        this.modelMapper = modelMapper;
        this.freelancerRepository = freelancerRepository;
        this.cloudinaryService = cloudinaryService;
        this.freelancerValidationService = freelancerValidationService;
    }

    @Override
    public boolean register(FreelancerServiceModel freelancerServiceModel,
                            String username) throws IOException {
        if(!this.freelancerValidationService.isValid(freelancerServiceModel)){
            throw new IllegalArgumentException(FREELANCER_VALIDATION_ERROR_MESSAGE);
        }

        UserServiceModel userServiceModel = this.userService.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Username does not exists"));

        Freelancer freelancer = mapFields(userServiceModel, freelancerServiceModel);

        String imageUrl = this.cloudinaryService.uploadImage(freelancerServiceModel.getImage());
        freelancer.setImageUrl(imageUrl);

        User user = this.modelMapper.map(userServiceModel, User.class);
        if(!this.userService.setUserAuthorities(user, Constants.ROLE_FREELANCER)){
            throw new IllegalArgumentException();
        }

        freelancer.setUser(user);
        try {
            this.freelancerRepository.save(freelancer);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Freelancer mapFields(UserServiceModel userServiceModel, FreelancerServiceModel freelancerServiceModel) {
        Freelancer freelancer = this.modelMapper.map(userServiceModel, Freelancer.class);

        freelancer.setAgeExperience(freelancerServiceModel.getAgeExperience());
        freelancer.setCertificateNumber(freelancerServiceModel.getCertificateNumber());
        freelancer.setMobileNumber(freelancerServiceModel.getMobileNumber());
        freelancer.setFee(freelancerServiceModel.getFee());

        return freelancer;
    }

}

package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.FreelancerAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerHireServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.FreelancerRepository;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.validation.FreelancerValidationService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class FreelancerServiceImpl implements FreelancerService {
    private static final String FREELANCER_VALIDATION_ERROR_MESSAGE = "Entered data is not correct!";

    private static final int MAX_ELEMENTS_PER_PAGE = 8;

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
    public boolean register(FreelancerAddServiceModel freelancerAddServiceModel,
                            String username) throws IOException {
        if (!this.freelancerValidationService.isValid(freelancerAddServiceModel)) {
            throw new IllegalArgumentException(FREELANCER_VALIDATION_ERROR_MESSAGE);
        }

        UserServiceModel userServiceModel = this.userService.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Username does not exists"));

        Freelancer freelancer = mapFields(userServiceModel, freelancerAddServiceModel);

        String imageUrl = this.cloudinaryService.uploadImage(freelancerAddServiceModel.getImage());
        freelancer.setImageUrl(imageUrl);

        User user = this.modelMapper.map(userServiceModel, User.class);
        if (!this.userService.setUserAuthorities(user, Constants.ROLE_FREELANCER)) {
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

    @Override
    public Page<FreelancerServiceModel> findPaginated(int page) {
        Pageable pageRequest = PageRequest.of(page - Constants.ONE, MAX_ELEMENTS_PER_PAGE);
        Page<Freelancer> freelancers = this.freelancerRepository.findAll(pageRequest);

        if (page > freelancers.getTotalPages()) {
            throw new NoSuchElementException(Constants.PAGE_ERROR_MESSAGE);
        }

        return freelancers.map(p -> this.modelMapper.map(p, FreelancerServiceModel.class));
    }

    @Override
    public Optional<FreelancerServiceModel> findById(String id) {
        Optional<Freelancer> freelancer = this.freelancerRepository.findById(id);
        return freelancer
                .map(m -> this.modelMapper.map(m, FreelancerServiceModel.class));

    }

    @Override
    public boolean checkIfAvailable(String startDateStr, String endDateStr) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        DateTime startDate = formatter.parseDateTime(startDateStr);
        DateTime endDate = formatter.parseDateTime(endDateStr);

        List<Date> dates = new ArrayList<>();

        int days = Days.daysBetween(startDate, endDate).getDays() + 1;
        for (int i = 0; i < days; i++) {
            DateTime d = startDate.withFieldAdded(DurationFieldType.days(), i);
            dates.add(d.toDate());
        }

        return false;
    }

    @Override
    public boolean hireFreelancer(FreelancerHireServiceModel freelancerHireServiceModel, String username) {
        List<Date> busyDates =
                getBusyDates(freelancerHireServiceModel.getStartDate(), freelancerHireServiceModel.getEndDate());

        Freelancer freelancer = this.freelancerRepository.findById(freelancerHireServiceModel.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        for (Date busyDate : busyDates) {
            freelancer.getEmployment().add(busyDate);
        }

        try {
            this.freelancerRepository.save(freelancer);
        } catch (Exception e) {
            return false;
        }

        return this.userService.setFreelancer(freelancer, username);
    }

    private Freelancer mapFields(UserServiceModel userServiceModel, FreelancerAddServiceModel freelancerAddServiceModel) {
        Freelancer freelancer = this.modelMapper.map(userServiceModel, Freelancer.class);

        freelancer.setAgeExperience(freelancerAddServiceModel.getAgeExperience());
        freelancer.setCertificateNumber(freelancerAddServiceModel.getCertificateNumber());
        freelancer.setMobileNumber(freelancerAddServiceModel.getMobileNumber());
        freelancer.setFee(freelancerAddServiceModel.getFee());

        return freelancer;
    }

    private List<Date> getBusyDates(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        DateTime startDate = formatter.parseDateTime(startDateStr);
        DateTime endDate = formatter.parseDateTime(endDateStr);

        List<Date> dates = new ArrayList<>();

        int days = Days.daysBetween(startDate, endDate).getDays() + 1;
        for (int i = 0; i < days; i++) {
            DateTime d = startDate.withFieldAdded(DurationFieldType.days(), i);
            dates.add(d.toDate());
        }

        return dates;
    }

}

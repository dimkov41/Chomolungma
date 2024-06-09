package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.Role;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.*;
import com.dimkov.bgMountains.repository.FreelancerRepository;
import com.dimkov.bgMountains.repository.UserRepository;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.validation.FreelancerValidationService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FreelancerServiceImpl implements FreelancerService {
    private static final String FREELANCER_VALIDATION_ERROR_MESSAGE = "Entered data is not correct!";

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final FreelancerRepository freelancerRepository;
    private final CloudinaryService cloudinaryService;
    private final FreelancerValidationService freelancerValidationService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public FreelancerServiceImpl(
            UserService userService,
            ModelMapper modelMapper,
            FreelancerRepository freelancerRepository,
            CloudinaryService cloudinaryService,
            FreelancerValidationService freelancerValidationService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userService = userService;
        this.modelMapper = modelMapper;
        this.freelancerRepository = freelancerRepository;
        this.cloudinaryService = cloudinaryService;
        this.freelancerValidationService = freelancerValidationService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
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
            this.userRepository.save(user);
            this.freelancerRepository.save(freelancer);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Page<FreelancerServiceModel> findPaginated(int page, int maxElements) {
        Pageable pageRequest = PageRequest.of(page - Constants.ONE, maxElements);
        Page<Freelancer> freelancers = this.freelancerRepository.findAll(pageRequest);
        return freelancers.map(p -> this.modelMapper.map(p, FreelancerServiceModel.class));
    }

    @Override
    public Optional<FreelancerServiceModel> findById(String id) {
        Optional<Freelancer> freelancer = this.freelancerRepository.findById(id);
        return freelancer
                .map(m -> this.modelMapper.map(m, FreelancerServiceModel.class));

    }

    @Override
    public boolean checkIfAvailable(String startDateStr, String endDateStr, String id) throws ParseException {
        Freelancer freelancer = this.freelancerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        List<DateTime> desiredDates =
                getDesiredDates(startDateStr, endDateStr);

        List<DateTime> freelancerBusyDates =
                freelancer
                        .getEmployment()
                        .stream()
                        .map(DateTime::new)
                        .collect(Collectors.toList());
        ;

        for (DateTime desiredDate : desiredDates) {
            for (DateTime freelancerBusyDate : freelancerBusyDates) {
                if (desiredDate.withTimeAtStartOfDay().isEqual(freelancerBusyDate.withTimeAtStartOfDay())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean makeChanges(FreelancerChangeServiceModel freelancerChangeServiceModel) {
        Freelancer freelancer = this.freelancerRepository.findByUserUsername(freelancerChangeServiceModel.getUsername())
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        if (!bCryptPasswordEncoder.matches(freelancerChangeServiceModel.getPassword(),
                freelancer.getUser().getPassword())){
            return false;
        }

           mapFields(freelancer,freelancerChangeServiceModel);

        try {
            this.freelancerRepository.save(freelancer);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean checkFreelacerExists(String username) {
        UserServiceModel userServiceModel =
                this.userService.findByUsername(username)
                        .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        Set<Role> roles = userServiceModel.getAuthorities();
        for (Role role : roles) {
            if (role.getAuthority().equalsIgnoreCase(Constants.ROLE_FREELANCER)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FreelancerServiceModel findByName(String username) {
        Freelancer freelancer = this.freelancerRepository.findByUserUsername(username)
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        return this.modelMapper.map(freelancer, FreelancerServiceModel.class);
    }

    @Override
    public boolean hireFreelancer(FreelancerHireServiceModel freelancerHireServiceModel, String username) throws ParseException {
        List<DateTime> desiredDates =
                getDesiredDates(freelancerHireServiceModel.getStartDate(), freelancerHireServiceModel.getEndDate());

        Freelancer freelancer = this.freelancerRepository.findById(freelancerHireServiceModel.getId())
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        if (!checkIfAvailable(freelancerHireServiceModel.getStartDate(), freelancerHireServiceModel.getEndDate(),
                freelancer.getId())) {
            return false;
        }

        for (DateTime busyDate : desiredDates) {
            freelancer.getEmployment().add(busyDate.toDate());
        }

        try {
            this.freelancerRepository.save(freelancer);
        } catch (Exception e) {
            return false;
        }

        return this.userService.setFreelancer(freelancer, username);
    }

    public boolean saveWorkingDates(String id, String startDate, String endDate) {
        try {
            Freelancer freelancer = this.freelancerRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

            if (!"".equals(endDate)) {
                List<DateTime> desiredDates = getDesiredDates(startDate, endDate);
                if (!checkIfAvailable(startDate, endDate, id)) {
                    //if already added - do nothing
                    return true;
                }
                for (DateTime busyDate : desiredDates) {
                    freelancer.getWorkingDates().add(busyDate.toDate());
                }
                this.freelancerRepository.save(freelancer);
            } else {
                List<DateTime> desiredDates = getDesiredDates(startDate, startDate);
                if (!checkIfAvailable(startDate, startDate, id)) {
                    //if already added - do nothing
                    return true;
                }
                for (DateTime busyDate : desiredDates) {
                    freelancer.getWorkingDates().add(busyDate.toDate());
                }
                this.freelancerRepository.save(freelancer);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean removeWorkingDates(String id, String startDate, String endDate) {
        try {
            Freelancer freelancer = this.freelancerRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

            if (!"".equals(endDate)) {
                List<DateTime> desiredDates  = getDesiredDates(startDate, endDate);
                if (checkIfAvailable(startDate, endDate, id)) {
                    //if already added - remove them
                    for (DateTime busyDate : desiredDates) {
                        freelancer.getWorkingDates().remove(busyDate.toDate());
                    }
                    this.freelancerRepository.save(freelancer);
                } else {
                    //do nothing if no added
                    return true;
                }
            } else {
                List<DateTime> desiredDates  = getDesiredDates(startDate, startDate);
                if (checkIfAvailable(startDate, startDate, id)) {
                    //if already added - remove them
                    for (DateTime busyDate : desiredDates) {
                        freelancer.getWorkingDates().remove(busyDate.toDate());
                    }
                    this.freelancerRepository.save(freelancer);
                } else {
                    //do nothing if no added
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Freelancer mapFields(UserServiceModel userServiceModel, FreelancerAddServiceModel freelancerAddServiceModel) {
        Freelancer freelancer = this.modelMapper.map(userServiceModel, Freelancer.class);

        freelancer.setAgeExperience(freelancerAddServiceModel.getAgeExperience());
        freelancer.setCertificateNumber(freelancerAddServiceModel.getCertificateNumber());
        freelancer.setMobileNumber(freelancerAddServiceModel.getMobileNumber());
        freelancer.setFee(freelancerAddServiceModel.getFee());
        freelancer.setFullName(freelancerAddServiceModel.getFullName());
        freelancer.setDescription(freelancerAddServiceModel.getDescription());
        freelancer.setEmployment(new ArrayList<>());

        return freelancer;
    }

    private Freelancer mapFields(Freelancer freelancer,FreelancerChangeServiceModel freelancerChangeServiceModel){
        freelancer.setAgeExperience(freelancerChangeServiceModel.getAgeExperience());
        freelancer.setDescription(freelancerChangeServiceModel.getDescription());
        freelancer.setMobileNumber(freelancerChangeServiceModel.getMobileNumber());
        freelancer.setFee(freelancerChangeServiceModel.getFee());

        return freelancer;
    }

    private List<DateTime> getDesiredDates(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        DateTime startDate = formatter.parseDateTime(startDateStr);
        DateTime endDate = formatter.parseDateTime(endDateStr);

        List<DateTime> dates = new ArrayList<>();

        int days = Days.daysBetween(startDate, endDate).getDays() + 1;
        for (int i = 0; i < days; i++) {
            DateTime d = startDate.withFieldAdded(DurationFieldType.days(), i);
            dates.add(d);
        }

        return dates;
    }

}

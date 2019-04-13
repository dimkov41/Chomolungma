package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.FreelancerAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerHireServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

public interface FreelancerService {
    boolean register(FreelancerAddServiceModel freelancerAddServiceModel,
                     String username) throws IOException;

    Page<FreelancerServiceModel> findPaginated(int page);

    Optional<FreelancerServiceModel> findById(String id);

    boolean checkIfAvailable(String startDateStr, String endDateStr, String id) throws ParseException;

    boolean checkFreelacerExists(String username);

    FreelancerServiceModel findByName(String name);

    boolean hireFreelancer(FreelancerHireServiceModel freelancerHireServiceModel, String username) throws ParseException;
}

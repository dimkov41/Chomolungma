package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserFreelancerServiceModel;
import com.dimkov.bgMountains.util.Constants;
import org.modelmapper.ModelMapper;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.RoleRepository;
import com.dimkov.bgMountains.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found!";

    private static final int MAX_ELEMENTS_PER_PAGE = 8;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user = this.giveRolesToUser(user);

        try {
            this.userRepository.save(user);

            return true;
        } catch (Exception e){
            return false;
        }
    }


    @Override
    public Optional<UserServiceModel> findByUsername(String username) {
        return this.userRepository.findByUsername(username).map(u -> this.modelMapper.map(u, UserServiceModel.class));
    }

    @Override
    public Optional<UserServiceModel> findByEmail(String email) {
        return this.userRepository.findByEmail(email).map(u -> this.modelMapper.map(u, UserServiceModel.class));
    }

    @Override
    public boolean setUserAuthorities(User user, String authority) {
        user.getAuthorities().clear();

        switch (authority){
            case Constants.ROLE_USER:
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_USER));
                break;
            case Constants.ROLE_FREELANCER:
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_USER));
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_FREELANCER));
                break;
            case Constants.ROLE_MODERATOR:
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_USER));
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_MODERATOR));
                break;
            case Constants.ROLE_ADMIN:
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_USER));
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_MODERATOR));
                user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_ADMIN));
                break;
        }

        try {
            this.userRepository.save(user);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    //    @Override
//    public Page<UserFreelancerServiceModel> findPaginatedFreelancers(int page) {
//        Pageable pageRequest = PageRequest.of(page - Constants.ONE, MAX_ELEMENTS_PER_PAGE);
//        Page<User> freelancers = this.userRepository.findAllByFreelancerTrue(pageRequest);
//
//        if(page > freelancers.getTotalPages()){
//            throw new NoSuchElementException(Constants.PAGE_ERROR_MESSAGE);
//        }
//
//        return freelancers.map(p -> this.modelMapper.map(p, UserFreelancerServiceModel.class));
//    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE));
    }

    private User giveRolesToUser(User user){
        if(this.userRepository.findAll().isEmpty()){
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ROOT"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        }

        return user;
    }
}

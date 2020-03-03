package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.Role;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserChangeServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.FreelancerRepository;
import com.dimkov.bgMountains.repository.RoleRepository;
import com.dimkov.bgMountains.repository.UserRepository;
import com.dimkov.bgMountains.util.Constants;
import com.dimkov.bgMountains.validation.UserValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found!";
    private static final String USERNAME_MODEL_NOT_VALID_MESSAGE = "UserServiceModel not valid";

    private static final int MAX_ELEMENTS_PER_PAGE = 8;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserValidationService userValidationService;
    private final FreelancerRepository freelancerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserValidationService userValidationService, FreelancerRepository freelancerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userValidationService = userValidationService;
        this.freelancerRepository = freelancerRepository;
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setAuthorities(new HashSet<>());
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
        boolean isFreelancer = false;

        List<String> authorities =
                user.getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList());
        if(authorities.contains(Constants.ROLE_FREELANCER)){
            isFreelancer = true;
        }

        user.getAuthorities().clear();

        switch (authority) {
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

        if(isFreelancer){
            user.getAuthorities().add(this.roleRepository.findByAuthority(Constants.ROLE_FREELANCER));
        }

        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean setUserAuthorities(String role,String id){
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        return setUserAuthorities(user, role);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE));
    }

    @Override
    public boolean setFreelancer(Freelancer freelancer, String username){
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        user.getHires().add(freelancer.getUser().getUsername());

        try{
            this.userRepository.save(user);
        } catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public Set<FreelancerServiceModel> getHiredFreelancers(String username){
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        Set<Freelancer> freelancerSet = new HashSet<>();

        Set<String> freelancerNames = new HashSet<>(user.getHires());
        for (String currentName : freelancerNames) {
            Freelancer freelancer = this.freelancerRepository.findByUserUsername(currentName)
                    .orElseThrow(NoSuchElementException::new);

            freelancerSet.add(freelancer);
        }


        return freelancerSet
                .stream()
                .map(f -> this.modelMapper.map(f,FreelancerServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean changePassword(UserChangeServiceModel userChangeServiceModel){
        if(!this.userValidationService.isChangeModelValid(userChangeServiceModel)){
            throw new IllegalArgumentException(USERNAME_MODEL_NOT_VALID_MESSAGE);
        }

        User user = this.userRepository.findByUsername(userChangeServiceModel.getUsername())
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        if(!userChangeServiceModel.getNewPassword().equals(userChangeServiceModel.getRepeatPassword()) ||
            !bCryptPasswordEncoder.matches(userChangeServiceModel.getOldPassword(), user.getPassword())){
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(userChangeServiceModel.getNewPassword()));

        try{
            this.userRepository.save(user);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public List<UserServiceModel> findAll(){
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
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

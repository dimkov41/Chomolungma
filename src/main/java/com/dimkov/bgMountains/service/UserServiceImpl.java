package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.UserFreelancerRegisterServiceModel;
import org.modelmapper.ModelMapper;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.RoleRepository;
import com.dimkov.bgMountains.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.channels.NoConnectionPendingException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found!";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    public boolean registerFreelancer(UserFreelancerRegisterServiceModel userFreelancerRegisterServiceModel,
                                      String username){
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Username does not exists"));

        setFreelancerFields(user, userFreelancerRegisterServiceModel);

        


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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE));
    }

    private User giveRolesToUser(User user){
        if(this.userRepository.findAll().isEmpty()){
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_FREELANCER"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ROOT"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        }

        return user;
    }

    private User setFreelancerFields(User user,
                                     UserFreelancerRegisterServiceModel userFreelancerRegisterServiceModel){
        user.setAgeExperience(userFreelancerRegisterServiceModel.getAgeExperience());
        user.setCertificateNumber(userFreelancerRegisterServiceModel.getCertificateNumber());
        user.setFee(userFreelancerRegisterServiceModel.getFee());
        user.setMobileNumber(userFreelancerRegisterServiceModel.getMobileNumber());

        return user;
    }
}

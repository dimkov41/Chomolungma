package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.RoleRepository;
import com.dimkov.bgMountains.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    private UserServiceModel userServiceModel;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private RoleRepository mockRoleRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void init(){
        this.userServiceModel = new UserServiceModel();
    }

    @Test
    public void test_registerUser_withCorrectData(){
        this.userService.register(userServiceModel);
        when(mockUserRepository.save(any()))
                .thenReturn(new User());

        verify(mockUserRepository)
                .save(any());
    }

    @Test
    public void test_registerUser_withIncorrectData(){
        boolean result = this.userService.register(userServiceModel);
        when(mockUserRepository.save(any()))
                .thenThrow(new IllegalArgumentException());

        Assert.assertTrue(result);
    }

    @Test
    public void test_findByUsername(){
        when(this.mockUserRepository.findByUsername("Pesho"))
                .thenReturn(Optional.of(new User()));
        Optional<UserServiceModel> userServiceModel = this.userService.findByUsername("Pesho");

        Assert.assertTrue(userServiceModel.isPresent());
    }

//    @Test
//    public void test_findByEmail(){
//        when(this.mockUserRepository.findByUsername("Pesho"))
//                .thenReturn(Optional.of(new User()));
//        Optional<UserServiceModel> userServiceModel = this.userService.findByUsername("Pesho");
//
//        Assert.assertTrue(userServiceModel.isPresent());
//    }


}

package com.training.RepAgency.service;

import com.training.RepAgency.dto.UserDTO;
import com.training.RepAgency.entity.User;
import com.training.RepAgency.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private  PasswordEncoder passwordEncoder;

    @Test
    public void saveUser() {
        User user = new User();
        Assert.assertTrue(userService.saveUser(user));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void saveUserFail() {
        User user = new User();

        user.setEmail("u@u.u");

        Mockito.doReturn(Optional.empty())
                .when(userRepository)
                .findByEmail("u@u.u");

        Assert.assertFalse(userService.saveUser(user));
        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    public void updatePassword() {

        User user = new User();
        user.setEmail("u@u.u");
        user.setId(1L);
        user.setPassword("pass");
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findByEmail("u@u.u");
        Assert.assertTrue(userService.updatePassword(UserDTO.builder()
                .password("pass")
                .email(user.getEmail())
                .build()));

        Mockito.verify(userRepository, Mockito.times(1))
                .updatePasswordById(passwordEncoder.encode(user.getPassword()), user.getId());
    }
}
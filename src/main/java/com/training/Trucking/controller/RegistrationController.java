package com.training.Trucking.controller;

import com.training.Trucking.dto.UserDTO;

import com.training.Trucking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;


    @GetMapping("/reg")
    public String getRegistrationPage(UserDTO userDTO) {
        return "reg.html";
    }

    @PostMapping("/reg")
    public String validateRegistrationInfo(Model model, @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reg.html";
        }
        userService.saveUser(userDTO);

        return "account-verified.html";

    }

}



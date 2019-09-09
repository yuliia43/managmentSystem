package com.training.RepAgency.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping(value = "/")
    public String getIndexPage() {
        return "index.html";
    }

    @GetMapping(value = "/user")
    public String userResolve() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
            return "redirect:user/create_request";
        }else if (auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_MANAGER"))) {
            return "redirect:manager/new_requests";
        }else if(auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_MASTER"))) {
            return "redirect:master/new_requests";
        }
        return "redirect:login";
    }
}



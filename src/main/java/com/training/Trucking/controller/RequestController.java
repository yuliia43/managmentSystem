package com.training.Trucking.controller;

import com.training.Trucking.dto.RequestDTO;
import com.training.Trucking.dto.UserDTO;
import com.training.Trucking.entity.Request;
import com.training.Trucking.entity.Role;
import com.training.Trucking.service.RequestService;
import com.training.Trucking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class RequestController {
    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @GetMapping("/user/create_request")
    public String getCreateRequestPage(Model model) {
        return "user-create-request.html";
    }

    @PostMapping("/user/create_request")
    public String createRequest(@RequestParam("request") String request,
                                Model model) {
        model.addAttribute("request", request);
        requestService.saveRequest(request, SecurityContextHolder.getContext().getAuthentication().getName());
        return "user-create-request.html";
    }

    @GetMapping("/user/all_requests")
    public String getAllRequestsPage(Model model) {
        model.addAttribute("userRequest", requestService.getRequestsByCreator(SecurityContextHolder.getContext()
                .getAuthentication().getName()));
        return "user-all-requests.html";
    }

    @GetMapping(value = "/manager/new_requests")
    public String getAdminCabinet(Model model) {

        List<Request> requests = requestService.getRequestsByStatus("new");
        model.addAttribute("newRequests", requests);

        //List<Role> roles=new ArrayList<>();
        //roles.add(new Role("ROLE_MASTER"));
        model.addAttribute("masters", userService.findByRole("ROLE_MASTER"));

        return "manager-all-requests.html";
    }

    @PostMapping(value = "/manager/new_requests")
    public String makeAcceptedOrRejected(@RequestParam("id") long id,
                               @RequestParam("master") String master,
                               @RequestParam("reason") String reason,
                               @RequestParam("price") Long price) {
        log.info("in post method");
        if (reason.isEmpty()) {
            requestService.updateStatusAndMasterById("rejected", id, null, reason, null);
            log.info("{}", "reject");
        } else {
            requestService.updateStatusAndMasterById("accepted", id, master, null, price);
            log.info("{}", "accept");
        }
        return "redirect:/manager/new_requests";
    }

    @GetMapping(value = "/master/new_requests")
    public String getAcceptedRequests(Model model) {
        try {
            log.info(SecurityContextHolder.getContext().getAuthentication().getName());

            model.addAttribute("newRequests", requestService.getRequestsByStatusAndEmail("accepted",
                    SecurityContextHolder.getContext().getAuthentication().getName()));

        } catch (Exception e) {
            model.addAttribute("error", "You have not requests");
        }
        return "master-new-requests.html";
    }

    @GetMapping(value = "/master/new_requests/in_process")
    public String makeRequestInProgress(@RequestParam("id") long id) {
        requestService.updateStatusById("in progress", id);
        return "redirect:/master/new_requests";
    }

    @GetMapping(value = "/master/in_progress_requests")
    public String getInProgressRequests(Model model) {
        try {
            log.info(SecurityContextHolder.getContext().getAuthentication().getName());

            model.addAttribute("inProgressRequests", requestService.getRequestsByStatusAndEmail("in progress",
                    SecurityContextHolder.getContext().getAuthentication().getName()));

        } catch (Exception e) {
            model.addAttribute("error", "You have not requests");
        }
        return "master-in-progress-requests.html";
    }

    @GetMapping(value = "/master/in_progress_requests/completed")
    public String makeRequestCompleted(@RequestParam("id") long id) {
        requestService.updateStatusById("completed", id);
        return "redirect:/master/in_progress_requests";
    }


    @GetMapping(value = "/master/completed_requests")
    public String getCompletedRequests(Model model) {
        try {
            log.info(SecurityContextHolder.getContext().getAuthentication().getName());

            model.addAttribute("completedRequests", requestService.getRequestsByStatusAndEmail("completed",
                    SecurityContextHolder.getContext().getAuthentication().getName()));

        } catch (Exception e) {
            model.addAttribute("error", "You have not requests");
        }
        return "master-completed-requests.html";
    }
}

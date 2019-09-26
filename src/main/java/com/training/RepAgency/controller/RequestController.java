package com.training.RepAgency.controller;

import com.training.RepAgency.dto.RequestInfoDTO;
import com.training.RepAgency.entity.Request;
import com.training.RepAgency.service.RequestService;
import com.training.RepAgency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;


@Slf4j
@Controller
public class RequestController {
    @Autowired
    private RequestService requestService;


    @Autowired
    private UserService userService;

    @GetMapping("/user/create_request")
    public String getCreateRequestPage(Model model) {
        return "user-create-request.html";
    }

    @PostMapping("/user/create_request")
    public String createRequest(@RequestParam("request") String request,
                                @RequestParam(value = "error", required = false) String error,
                                Model model) {
        model.addAttribute("request", request);
        if (request.isEmpty()) {
            model.addAttribute("error", error != null);
        } else {
            requestService.saveRequest(request, SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return "user-create-request.html";
    }

    @GetMapping("/user/all_requests")
    public String getAllRequestsPage(Model model, Pageable pageable) {
        model.addAttribute("userRequest", requestService.getRequestsByCreator(SecurityContextHolder.getContext()
                .getAuthentication().getName(),"rejected", pageable));

        requestService.getByCreatorAndStatus(
                SecurityContextHolder.getContext()
                        .getAuthentication().getName(), "rejected")
                .ifPresent(r->model.addAttribute("rejectedRequests",r));
        return "user-all-requests.html";
    }

    @GetMapping(value = "/manager/new_requests")
    public String getAdminCabinet(Model model, @SortDefault("request") Pageable pageable) {

        Page<Request> requests = requestService.getRequestsByStatus("new", pageable);
        model.addAttribute("newRequests", requests);

        return "manager-all-requests.html";
    }

    @GetMapping(value = "/manager/new_requests/accept")
    public String getAcceptedId(@RequestParam("id") long id, RequestInfoDTO requestDto, Model model) {
        requestDto.setId(id);
        model.addAttribute("requestDto", requestDto);
        log.info("{}", id);
        model.addAttribute("masters", userService.findByRole("ROLE_MASTER"));
        return "manager-accept-request.html";
    }

    @PostMapping(value = "/manager/new_requests/accept/req")
    public String makeAccepted(Model model, RequestInfoDTO requestDto) {
        log.info("{}", requestDto.getId());

        requestService.updateStatusAndMasterById("accepted", requestDto.getId(), requestDto.getMaster(), null,
                requestDto.getPrice());
        log.info("{}", "accept");
        return "redirect:/manager/new_requests";
    }

    @GetMapping(value = "/manager/new_requests/reject")
    public String getRejectedId(@RequestParam("id") long id, RequestInfoDTO requestDto, Model model) {
        requestDto.setId(id);
        model.addAttribute("requestDto", requestDto);
        return "manager-reject-request.html";
    }

    @PostMapping(value = "/manager/new_requests/reject/req")
    public String makeRejected(RequestInfoDTO requestDto) {

        requestService.updateStatusAndMasterById("rejected", requestDto.getId(), null,
                requestDto.getReason(), 0L);

        return "redirect:/manager/new_requests";
    }

    @GetMapping(value = "/master/new_requests")
    public String getAcceptedRequests(Model model, Pageable pageable) {
        try {
            log.info(SecurityContextHolder.getContext().getAuthentication().getName());

            model.addAttribute("newRequests", requestService.getRequestsByStatusAndEmail("accepted",
                    SecurityContextHolder.getContext().getAuthentication().getName(), pageable));

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
    public String getInProgressRequests(Model model, Pageable pageable) {
        try {
            log.info(SecurityContextHolder.getContext().getAuthentication().getName());

            model.addAttribute("inProgressRequests", requestService.getRequestsByStatusAndEmail("in progress",
                    SecurityContextHolder.getContext().getAuthentication().getName(), pageable));

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
    @GetMapping(value = "/master/in_progress_requests/beyond_repair")
    public String makeRequestBeyondRepair(@RequestParam("id") long id) {
        requestService.updateStatusAndReasonById("rejected", id,"beyond repair");
        return "redirect:/master/in_progress_requests";
    }


    @GetMapping(value = "/master/completed_requests")
    public String getCompletedRequests(Model model, Pageable pageable) {
        try {
            log.info(SecurityContextHolder.getContext().getAuthentication().getName());

            model.addAttribute("completedRequests", requestService.getRequestsByStatusAndEmail("completed",
                    SecurityContextHolder.getContext().getAuthentication().getName(), pageable));

        } catch (Exception e) {
            model.addAttribute("error", "You have not requests");
        }
        return "master-completed-requests.html";
    }
}

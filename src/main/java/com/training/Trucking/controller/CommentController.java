package com.training.Trucking.controller;

import com.training.Trucking.dto.CommentDTO;
import com.training.Trucking.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/user/create_comment")
    public String getCreateRequestPage(Model model) {
        //model.addAttribute("request", "");
        return "user-create-comment.html";
    }

    @PostMapping("/user/create_comment")
    public String createComment(@RequestParam("comment") String comment,
                                @RequestParam(value = "error", required = false) String error,
                                Model model) {
        model.addAttribute("comment", comment);
        if (comment.isEmpty()) {
            model.addAttribute("error", error != null);
        } else {
            commentService.saveComment(CommentDTO.builder()
                    .comment(comment)
                    .date(LocalDate.now())
                    .username(SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName())
                    .build());
        }
        //ConfirmationToken confirmationToken = new ConfirmationToken(userService.findByEmail(userDTO.getEmail()).get());
        //confirmationTokenService.save(confirmationToken);
        //emailSenderService.sendEmail(userDTO.getEmail(), confirmationToken);
        return "user-create-comment.html";
        //return "account-need-verification.html";
    }

    @GetMapping("/manager/all_comments")
    public String getAllComments(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "manager-all-comments.html";
    }


}

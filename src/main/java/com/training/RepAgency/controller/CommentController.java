package com.training.RepAgency.controller;

import com.training.RepAgency.dto.CommentDTO;
import com.training.RepAgency.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/user/create_comment")
    public String getCreateRequestPage(Model model) {
        //model.addAttribute("request", "");
        return "user-create-comment.html";
    }

    //TODO: ping("/user/create_comment")
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
                            .getContext().getAuthentication().getName())
                    .build());
        }
        return "user-create-comment.html";
    }

    @GetMapping("/manager/all_comments")
    public String getAllComments(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "manager-all-comments.html";
    }
}

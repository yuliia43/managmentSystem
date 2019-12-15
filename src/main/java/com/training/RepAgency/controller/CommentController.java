package com.training.RepAgency.controller;

import com.training.RepAgency.dto.CommentDTO;
import com.training.RepAgency.entity.Comment;
import com.training.RepAgency.service.CommentService;
import com.training.RepAgency.service.RequestService;
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
    private  CommentService commentService;
    @Autowired
    private RequestService requestService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/master/create_comment")
    public String getCreateRequestPage(Model model) {
        //model.addAttribute("request", "");
        return "user-create-comment.html";
    }

    @PostMapping("/master/create_comment")
    public String createComment(@RequestParam("comment") String comment,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam("id") long id,
                                Model model) {
        model.addAttribute("comment", comment);
        if (comment.isEmpty()) {
            model.addAttribute("error", error != null);
        } else {
            commentService.saveComment(Comment.builder()
                    .comment(comment)
                    .date(LocalDate.now())
                    .request(requestService.findRequestById(id))
                    .build());
            model.addAttribute("success", true);
        }
        return "redirect:/master/in_progress_requests";
    }

    @GetMapping("/manager/all_comments")
    public String getAllComments(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "manager-all-comments.html";
    }
}

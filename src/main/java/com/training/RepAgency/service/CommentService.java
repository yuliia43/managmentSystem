package com.training.RepAgency.service;

import com.training.RepAgency.dto.CommentDTO;
import com.training.RepAgency.entity.Comment;
import com.training.RepAgency.repository.CommentRepository;
import com.training.RepAgency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = Optional.ofNullable(commentRepository.findAll())
                .orElseThrow(RuntimeException::new);

        return comments.stream().map(c -> CommentDTO.builder()

                .date(c.getDate())
                .comment(c.getComment())
                .build()).collect(Collectors.toList());

    }
}

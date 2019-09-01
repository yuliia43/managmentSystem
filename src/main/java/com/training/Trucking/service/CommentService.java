package com.training.Trucking.service;

import com.training.Trucking.dto.CommentDTO;
import com.training.Trucking.entity.Comment;
import com.training.Trucking.repository.CommentRepository;
import com.training.Trucking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment saveComment(CommentDTO commentDTO) {
        return commentRepository.save(Comment.builder()
                .comment(commentDTO.getComment())
                .user(userRepository.findByEmail(commentDTO.getUsername()).get())
                .date(commentDTO.getDate())
                .build());
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = Optional.ofNullable(commentRepository.findAll())
                .orElseThrow(RuntimeException::new);

        return comments.stream().map(c -> CommentDTO.builder()
                .username(c.getUser().getEmail())
                .date(c.getDate())
                .comment(c.getComment())
                .build()).collect(Collectors.toList());

    }
}

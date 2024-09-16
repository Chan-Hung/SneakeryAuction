package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.CommentConverter;
import com.hung.sneakery.dto.CommentDTO;
import com.hung.sneakery.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommentConverterImpl implements CommentConverter {

    @Override
    public CommentDTO convertToCommentDTO(Comment comment) {

        List<CommentDTO> replies = comment.getReplies() != null ? comment.getReplies().stream()
                .sorted(Comparator.comparing(Comment::getCreatedDate)) // Ensure replies are sorted by createdAt
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList()) : new ArrayList<>();

        return CommentDTO.builder()
                .id(comment.getId())
                .commentText(comment.getCommentText())
                .createdAt(comment.getCreatedDate())
                .userName(comment.getUser().getUsername())
                .parentCommentId(Optional.ofNullable(comment.getParentComment()).map(Comment::getId).orElse(null))
                .replies(replies)
                .build();
    }

    @Override
    public List<CommentDTO> convertToCommentDTOList(List<Comment> comments) {
        return Optional.ofNullable(comments).orElse(Collections.emptyList())
                .stream().map(this::convertToCommentDTO).collect(Collectors.toList());
    }
}

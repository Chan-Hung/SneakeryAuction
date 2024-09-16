package com.hung.sneakery.converter;

import com.hung.sneakery.dto.CommentDTO;
import com.hung.sneakery.entity.Comment;

import java.util.List;

public interface CommentConverter {

    CommentDTO convertToCommentDTO(Comment comment);

    List<CommentDTO> convertToCommentDTOList(List<Comment> comments);
}

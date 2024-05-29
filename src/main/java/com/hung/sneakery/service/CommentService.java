package com.hung.sneakery.service;

import com.hung.sneakery.dto.CommentDTO;
import com.hung.sneakery.dto.request.CommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Page<CommentDTO> getAllByProduct(Long productId, Pageable pageable);

    CommentDTO create(CommentRequest request);

    CommentDTO update(Long id, CommentRequest request);

    CommentDTO delete(Long id);
}

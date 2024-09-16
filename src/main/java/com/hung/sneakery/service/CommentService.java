package com.hung.sneakery.service;

import com.hung.sneakery.dto.CommentDTO;
import com.hung.sneakery.dto.request.CommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    /**
     * Get all Comment by Product
     *
     * @param productId Long
     * @param pageable  Pageable
     * @return Page<CommentDTO>
     */
    Page<CommentDTO> getAllByProduct(Long productId, Pageable pageable);

    /**
     * Create Comment
     *
     * @param request CommentRequest
     * @return CommentDTO
     */
    CommentDTO create(CommentRequest request);

    /**
     * Update Comment
     *
     * @param id      Long
     * @param request CommentRequest
     * @return CommentDTO
     */
    CommentDTO update(Long id, CommentRequest request);

    /**
     * Delete Comment
     *
     * @param id Long
     * @return CommentDTO
     */
    CommentDTO delete(Long id);
}

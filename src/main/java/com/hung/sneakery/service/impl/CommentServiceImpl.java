package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.CommentConverter;
import com.hung.sneakery.dto.CommentDTO;
import com.hung.sneakery.dto.request.CommentRequest;
import com.hung.sneakery.entity.Comment;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.CommentRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.service.CommentService;
import com.hung.sneakery.utils.SneakeryConstant;
import com.hung.sneakery.utils.SneakeryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private SneakeryUtil sneakeryUtil;

    @Resource
    private CommentConverter commentConverter;

    @Override
    public Page<CommentDTO> getAllByProduct(final Long productId, final Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findByProductIdAndParentCommentIsNull(productId, pageable);
        List<CommentDTO> commentDTOs = commentConverter.convertToCommentDTOList(commentsPage.getContent());
        return new PageImpl<>(commentDTOs, pageable, commentsPage.getTotalElements());
    }

    @Override
    public CommentDTO create(final CommentRequest request) {
        User user = sneakeryUtil.getCurrentUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(SneakeryConstant.PRODUCT_NOT_FOUND));

        Comment comment = Comment.builder()
                .commentText(request.getCommentText())
                .build();
        comment.setUser(user);
        comment.setProduct(product);
        if (request.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findByIdAndProductId(request.getParentCommentId(), request.getProductId()).orElseThrow(() -> new NotFoundException(SneakeryConstant.COMMENT_NOT_FOUND));
            comment.setParentComment(parentComment);
        }
        commentRepository.save(comment);
        return commentConverter.convertToCommentDTO(comment);
    }

    @Override
    public CommentDTO update(final Long id, final CommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SneakeryConstant.COMMENT_NOT_FOUND));
        comment.setCommentText(request.getCommentText());
        commentRepository.save(comment);
        return commentConverter.convertToCommentDTO(comment);
    }

    @Override
    public CommentDTO delete(final Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SneakeryConstant.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
        return commentConverter.convertToCommentDTO(comment);
    }
}

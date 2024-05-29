package com.hung.sneakery.repository;

import com.hung.sneakery.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByProductIdAndParentCommentIsNull(Long productId, Pageable pageable);

    Optional<Comment> findByIdAndProductId(Long parentCommentId, Long productId);

}

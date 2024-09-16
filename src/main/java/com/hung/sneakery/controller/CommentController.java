package com.hung.sneakery.controller;

import com.hung.sneakery.dto.CommentDTO;
import com.hung.sneakery.dto.request.CommentRequest;
import com.hung.sneakery.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "Comment APIs")
@RequestMapping("/comments")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/product/{productId}")
    public Page<CommentDTO> getCommentsByProduct(final Pageable pageable, @PathVariable Long productId) {
        return commentService.getAllByProduct(productId, pageable);
    }

    @PostMapping
    public CommentDTO create(@RequestBody @Valid final CommentRequest commentRequest) {
        return commentService.create(commentRequest);
    }

    @PutMapping("/{id}")
    public CommentDTO update(@PathVariable final Long id, @RequestBody @Valid final CommentRequest commentRequest) {
        return commentService.update(id, commentRequest);
    }

    @DeleteMapping("/{id}")
    public CommentDTO delete(@PathVariable final Long id) {
        return commentService.delete(id);
    }
}

package com.hung.sneakery.controller;

import com.hung.sneakery.dto.FeedbackDTO;
import com.hung.sneakery.dto.request.FeedbackRequest;
import com.hung.sneakery.service.FeedbackService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "Feedback APIs")
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @GetMapping("/seller/{sellerId}")
    public Page<FeedbackDTO> getFeedbacksBySeller(@PathVariable Long sellerId, final Pageable pageable) {
        return feedbackService.getAllBySeller(sellerId, pageable);
    }

    @PostMapping
    public FeedbackDTO create(@RequestBody @Valid final FeedbackRequest request) {
        return feedbackService.create(request);
    }

    @PutMapping("/{id}")
    public FeedbackDTO update(@PathVariable final Long id, @RequestBody @Valid final FeedbackRequest request) {
        return feedbackService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public FeedbackDTO delete(@PathVariable final Long id) {
        return feedbackService.delete(id);
    }
}

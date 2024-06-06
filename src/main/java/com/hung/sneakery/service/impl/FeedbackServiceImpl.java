package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.FeedbackConverter;
import com.hung.sneakery.dto.FeedbackDTO;
import com.hung.sneakery.dto.request.FeedbackRequest;
import com.hung.sneakery.entity.Feedback;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.FeedbackRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.service.FeedbackService;
import com.hung.sneakery.utils.SneakeryConstant;
import com.hung.sneakery.utils.SneakeryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackRepository feedbackRepository;

    @Resource
    private FeedbackConverter feedbackConverter;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private SneakeryUtil sneakeryUtil;

    @Override
    public Page<FeedbackDTO> getAllBySeller(Long sellerId, Pageable pageable) {
        Page<Feedback> feedbacksPage = feedbackRepository.findBySellerId(sellerId, pageable);
        List<FeedbackDTO> feedbackDTOs = feedbackConverter.convertToFeedbackDTOList(feedbacksPage.getContent());
        return new PageImpl<>(feedbackDTOs, pageable, feedbacksPage.getTotalElements());
    }

    @Override
    public FeedbackDTO create(FeedbackRequest request) {
        User user = sneakeryUtil.getCurrentUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(SneakeryConstant.PRODUCT_NOT_FOUND));

        Feedback feedback = Feedback.builder()
                .rating(request.getRating())
                .feedbackText(request.getFeedbackText())
                .product(product)
                .winner(user)
                .seller(product.getUser())
                .build();
        feedbackRepository.save(feedback);
        return feedbackConverter.convertToFeedbackDTO(feedback);
    }

    @Override
    public FeedbackDTO update(Long id, FeedbackRequest request) {
        return null;
    }

    @Override
    public FeedbackDTO delete(Long id) {
        return null;
    }
}

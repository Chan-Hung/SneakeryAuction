package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.FeedbackConverter;
import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.converter.UserConverter;
import com.hung.sneakery.dto.FeedbackDTO;
import com.hung.sneakery.entity.Feedback;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FeedbackConverterImpl implements FeedbackConverter {

    @Resource
    private ProductConverter productConverter;

    @Resource
    private UserConverter userConverter;

    @Override
    public FeedbackDTO convertToFeedbackDTO(Feedback feedback) {
        return FeedbackDTO.builder()
                .id(feedback.getId())
                .createdAt(feedback.getCreatedDate())
                .rating(feedback.getRating())
                .feedbackText(feedback.getFeedbackText())
                .productDTO(productConverter.convertToProductDTO(feedback.getProduct()))
                .winner(userConverter.convertToUserDTO(feedback.getWinner()))
                .seller(userConverter.convertToUserDTO(feedback.getSeller()))
                .build();
    }

    @Override
    public List<FeedbackDTO> convertToFeedbackDTOList(List<Feedback> feedbacks) {
        return Optional.ofNullable(feedbacks).orElse(Collections.emptyList())
                .stream().map(this::convertToFeedbackDTO).collect(Collectors.toList());
    }
}

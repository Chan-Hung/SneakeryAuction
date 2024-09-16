package com.hung.sneakery.converter;

import com.hung.sneakery.dto.FeedbackDTO;
import com.hung.sneakery.entity.Feedback;

import java.util.List;

public interface FeedbackConverter {

    FeedbackDTO convertToFeedbackDTO(Feedback feedback);

    List<FeedbackDTO> convertToFeedbackDTOList(List<Feedback> feedbacks);
}

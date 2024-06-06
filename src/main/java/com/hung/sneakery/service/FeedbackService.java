package com.hung.sneakery.service;

import com.hung.sneakery.dto.FeedbackDTO;
import com.hung.sneakery.dto.request.FeedbackRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    Page<FeedbackDTO> getAllBySeller(Long sellerId, Pageable pageable);

    FeedbackDTO create(FeedbackRequest request);

    FeedbackDTO update(Long id, FeedbackRequest request);

    FeedbackDTO delete(Long id);
}

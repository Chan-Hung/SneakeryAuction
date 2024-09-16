package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.EPaymentType;

public interface StripeService {

    /**
     * Process Payment
     *
     * @param paymentRequest PaymentRequest
     * @return BaseResponse
     */
    BaseResponse processPayment(PaymentRequest paymentRequest);

    /**
     * Handle Successful Payment
     *
     * @param checkoutSessionId String
     * @param type              EPaymentType
     * @return BaseResponse
     */
    BaseResponse handleSuccessPayment(String checkoutSessionId, EPaymentType type, Long productId);
}

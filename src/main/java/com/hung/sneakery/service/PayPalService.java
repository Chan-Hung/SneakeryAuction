package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.EPaymentType;

public interface PayPalService {

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
     * @param paymentId String
     * @param payerId   String
     * @param type      EPaymentType
     * @return BaseResponse
     */
    BaseResponse handleSuccessPayment(String paymentId, String payerId, EPaymentType type);
}

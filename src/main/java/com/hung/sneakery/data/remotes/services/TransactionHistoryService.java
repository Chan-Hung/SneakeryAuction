package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface TransactionHistoryService {
    Payment createPayment(Long userId, String cancelUrl, String successUrl) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    BaseResponse handleSuccess(Payment payment, Long userId);
}

package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.request.DepositRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface TransactionHistoryService {
    Payment createPayment(DepositRequest depositRequest) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    BaseResponse handleSuccess(Payment payment);
}

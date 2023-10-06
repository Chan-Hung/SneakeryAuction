package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.DepositRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.entity.TransactionHistory;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.List;

public interface TransactionHistoryService {

    Payment createPayment(DepositRequest depositRequest) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    BaseResponse handleSuccess(Payment payment);

    DataResponse<List<TransactionHistory>> getAllByWallet();

    BaseResponse paidByWinner(Long orderId, Long shippingFee, Long subtotal);

    BaseResponse withdraw(Long amount);
}

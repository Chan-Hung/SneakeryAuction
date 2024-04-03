package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.TransactionHistory;
import com.hung.sneakery.enums.EPaymentType;
import com.paypal.api.payments.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionHistoryService {

    /**
     * Create Payment
     *
     * @param request PaymentRequest
     * @return Payment
     */
    Payment createPayment(PaymentRequest request);

    /**
     * Execute Payment
     *
     * @param paymentId String
     * @param payerId   String
     * @return Payment
     */
    Payment executePayment(String paymentId, String payerId);

    /**
     * Handle Success
     *
     * @param payment Payment
     * @param type    EPaymentType
     * @return BaseResponse
     */
    BaseResponse handleSuccess(Payment payment, EPaymentType type);

    /**
     * Get All Transaction History By Wallet
     *
     * @param walletId Long
     * @param pageable Pageable
     * @return Page<TransactionHistory>
     */
    Page<TransactionHistory> getByWallet(Long walletId, Pageable pageable);

    /**
     * Paid By Winner
     *
     * @param orderId     Long
     * @param shippingFee Long
     * @param subtotal    Long
     * @return BaseResponse
     */
    BaseResponse paidByWinner(Long orderId, Long shippingFee, Long subtotal);

    /**
     * Withdraw Money
     *
     * @param amount Long
     * @return BaseResponse
     */
    BaseResponse withdraw(Long amount);
}

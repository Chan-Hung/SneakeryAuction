package com.hung.sneakery.service;

import com.hung.sneakery.dto.request.DepositRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.TransactionHistory;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.List;

public interface TransactionHistoryService {

    /**
     * Create Payment
     *
     * @param depositRequest DepositRequest
     * @return Payment
     * @throws PayPalRESTException PayPalRESTException
     */
    Payment createPayment(DepositRequest depositRequest) throws PayPalRESTException;

    /**
     * Execute Payment
     *
     * @param paymentId String
     * @param payerId   String
     * @return Payment
     * @throws PayPalRESTException PayPalRESTException
     */
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    /**
     * Handle Success
     *
     * @param payment Payment
     * @return BaseResponse
     */
    BaseResponse handleSuccess(Payment payment);

    /**
     * Get All Transaction History By Wallet
     *
     * @return List<TransactionHistory>
     */
    List<TransactionHistory> getAllByWallet();

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

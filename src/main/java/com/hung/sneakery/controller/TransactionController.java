package com.hung.sneakery.controller;

import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.TransactionHistory;
import com.hung.sneakery.enums.EPaymentType;
import com.hung.sneakery.service.TransactionHistoryService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Transaction By PayPal APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000/", "https://sneakery.vercel.app/", "https://www.sandbox.paypal.com/"})
@RequestMapping("/transactions")
public class TransactionController {

    @Resource
    private TransactionHistoryService transactionHistoryService;

    @PostMapping("/payment")
    public BaseResponse payment(@RequestBody final PaymentRequest paymentRequest) {
        Payment payment = transactionHistoryService.createPayment(paymentRequest);
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return new BaseResponse(true, link.getHref());
            }
        }
        return new BaseResponse(false, "PayPal is not available now, please contact to our customer service");
    }

    @GetMapping("/cancel")
    public BaseResponse cancelPay() {
        return new BaseResponse(false, "cancel");
    }

    @GetMapping("/success")
    public BaseResponse successPay(@RequestParam("paymentId") final String paymentId,
                                   @RequestParam("payerId") final String payerId,
                                   @RequestParam("paymentType") final EPaymentType type) {
        Payment payment = transactionHistoryService.executePayment(paymentId, payerId);
        if (payment.getState().equals("approved")) {
            return transactionHistoryService.handleSuccess(payment, type);
        }
        return new BaseResponse(false, "PayPal is not available now, please contact to our customer service");
    }

    @GetMapping("/{walletId}")
    public Page<TransactionHistory> getByWallet(@PathVariable final Long walletId, final Pageable pageable) {
        return transactionHistoryService.getByWallet(walletId, pageable);
    }

    @GetMapping("/withdraw")
    public BaseResponse withdraw(@RequestParam(name = "amount") final Long amount) {
        return transactionHistoryService.withdraw(amount);
    }

    @GetMapping("/paid")
    public BaseResponse paidByWinner(
            @RequestParam(name = "orderId") final Long orderId,
            @RequestParam(name = "shippingFee") final Long shippingFee,
            @RequestParam(name = "subtotal") final Long subtotal) {
        return transactionHistoryService.paidByWinner(orderId, shippingFee, subtotal);
    }
}

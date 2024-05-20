package com.hung.sneakery.controller;

import com.hung.sneakery.dto.TransactionDTO;
import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.enums.EPaymentType;
import com.hung.sneakery.service.PayPalService;
import com.hung.sneakery.service.StripeService;
import com.hung.sneakery.service.TransactionService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Transaction APIs")
@RequestMapping("/transactions")
public class TransactionController {

    @Resource
    private TransactionService transactionService;

    @Resource
    private PayPalService payPalService;

    @Resource
    private StripeService stripeService;

    @PostMapping("/paypal")
    public BaseResponse payWithPayPal(@RequestBody final PaymentRequest paymentRequest) {
        return payPalService.processPayment(paymentRequest);
    }

    @GetMapping("/paypal/success")
    public BaseResponse successPayWithPayPal(@RequestParam("paymentId") final String paymentId,
                                             @RequestParam("payerId") final String payerId,
                                             @RequestParam("paymentType") final EPaymentType type,
                                             @RequestParam("productId") final Long productId) {
        return payPalService.handleSuccessPayment(paymentId, payerId, type, productId);
    }

    @PostMapping("/stripe")
    public BaseResponse payWithStripe(@RequestBody final PaymentRequest paymentRequest) {
        return stripeService.processPayment(paymentRequest);
    }

    @GetMapping("/stripe/success")
    public BaseResponse successPayWithStripe(@RequestParam("sessionId") final String checkoutSessionId,
                                             @RequestParam("paymentType") final EPaymentType type,
                                             @RequestParam("productId") final Long productId) {
        return stripeService.handleSuccessPayment(checkoutSessionId, type, productId);
    }

    @GetMapping("/user-history")
    public Page<TransactionDTO> getTransactionHistoryByUser(Pageable pageable) {
        return transactionService.getAllByUser(pageable);
    }
}

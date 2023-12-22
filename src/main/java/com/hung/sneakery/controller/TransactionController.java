package com.hung.sneakery.controller;

import com.hung.sneakery.dto.request.DepositRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.TransactionHistory;
import com.hung.sneakery.exception.PayPalTransactionException;
import com.hung.sneakery.service.TransactionHistoryService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Transaction By PayPal APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000/", "https://sneakery.vercel.app/", "https://www.sandbox.paypal.com/"})
@RequestMapping("/transaction")
public class TransactionController {

    @Resource
    private TransactionHistoryService transactionHistoryService;

    @PostMapping("/deposit")
    public BaseResponse payment(@RequestBody final DepositRequest depositRequest) {
        try {
            Payment payment = transactionHistoryService.createPayment(depositRequest);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new BaseResponse(true, link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            throw new PayPalTransactionException(e.getMessage());
        }
        return new BaseResponse(false, "PayPal is not available now, please contact to our customer service");
    }

    @GetMapping("/deposit/cancel")
    public BaseResponse cancelPay() {
        return new BaseResponse(false, "cancel");
    }

    @GetMapping("/deposit/success")
    public BaseResponse successPay(@RequestParam("paymentId") final String paymentId,
                                   @RequestParam("payerId") final String payerId) {
        try {
            Payment payment = transactionHistoryService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return transactionHistoryService.handleSuccess(payment);
            }
        } catch (PayPalRESTException e) {
            throw new PayPalTransactionException(e.getMessage());
        }
        return new BaseResponse(false, "PayPal is not available now, please contact to our customer service");
    }

    @GetMapping()
    public List<TransactionHistory> getAllByWallet() {
        return transactionHistoryService.getAllByWallet();
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

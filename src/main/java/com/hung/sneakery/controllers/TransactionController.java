package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.TransactionHistoryService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/","http://localhost:3000"})
@RequestMapping("/api/paypal")
public class TransactionController {
    @Autowired
    TransactionHistoryService transactionHistoryService;

    public static final String SUCCESS_URL = "/success";
    public static final String CANCEL_URL = "/cancel";


    @PostMapping("/deposit/{userId}")
    public ResponseEntity<BaseResponse> payment(@PathVariable Long userId) {
        try {
            Payment payment = transactionHistoryService.createPayment(userId, "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity
                            .ok(new BaseResponse(true,
                                    link.getHref()));
                }
            }

        } catch (PayPalRESTException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(false,
                        "Paypal is not available now, please contact to our customer service"));
    }

    @GetMapping(CANCEL_URL)
    public ResponseEntity<?> cancelPay() {
        return ResponseEntity.ok(new BaseResponse(false, "cancel"));
    }

    @GetMapping(SUCCESS_URL)
    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId,
                                        @RequestParam("payerId") String payerId,
                                        @RequestParam("userId") Long userId) {
        try {
            Payment payment = transactionHistoryService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // Do update here
                return ResponseEntity
                        .ok(transactionHistoryService.handleSuccess(payment, userId));

            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity
                .ok(new BaseResponse(false,
                        "There is some thing wrong !"));
    }

}

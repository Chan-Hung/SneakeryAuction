package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.dto.request.DepositRequest;
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
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000/", "https://sneakery.vercel.app/","https://www.sandbox.paypal.com/"})
@RequestMapping("/api/transaction")//Remind Kiet to change this path
public class TransactionController {
    @Autowired
    TransactionHistoryService transactionHistoryService;

    @PostMapping("/deposit")
    public ResponseEntity<BaseResponse> payment(@RequestBody DepositRequest depositRequest) {
        try {
            Payment payment = transactionHistoryService.createPayment(depositRequest);
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

    @GetMapping("/deposit/cancel")
    public ResponseEntity<BaseResponse> cancelPay() {
        return ResponseEntity
                .ok(new BaseResponse(false, "cancel"));
    }

    @GetMapping("/deposit/success")
    public ResponseEntity<BaseResponse> successPay(@RequestParam("paymentId") String paymentId,
                                        @RequestParam("payerId") String payerId) {
        try {
            Payment payment = transactionHistoryService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return ResponseEntity
                        .ok(transactionHistoryService.handleSuccess(payment));
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity
                .ok(new BaseResponse(false,
                        "Paypal is not available now, please contact to our customer service"));//
    }

    @GetMapping("/get")
    public ResponseEntity<BaseResponse> getAllByWallet(){
        try{
            return ResponseEntity
                    .ok(transactionHistoryService.getAllByWallet());
        }catch(RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false,
                            e.getMessage()));
        }
    }

}

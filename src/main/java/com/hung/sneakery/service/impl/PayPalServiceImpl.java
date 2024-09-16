package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.Transaction;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EPaymentType;
import com.hung.sneakery.enums.PaymentStatus;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.repository.TransactionRepository;
import com.hung.sneakery.service.PayPalService;
import com.hung.sneakery.utils.SneakeryConstant;
import com.hung.sneakery.utils.SneakeryUtil;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PayPalServiceImpl implements PayPalService {

    @Resource
    private APIContext apiContext;

    @Resource
    private TransactionRepository transactionRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private SneakeryUtil sneakeryUtil;

    private static final String CLIENT_BASE_URL = System.getenv("CLIENT_BASE_URL");

    @Override
    @SneakyThrows
    public BaseResponse processPayment(final PaymentRequest request) {
        ItemList itemList = buildItemList(request);
        Amount amount = buildAmount(request);
        com.paypal.api.payments.Transaction transaction = buildTransaction(itemList, amount);
        Payment payment = buildPayment(transaction);

        RedirectUrls redirectUrls = buildRedirectUrls();
        payment.setRedirectUrls(redirectUrls);
        apiContext.setMaskRequestId(true);

        Payment approvedPayment = payment.create(apiContext);
        for (Links link : approvedPayment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return new BaseResponse(true, link.getHref());
            }
        }
        return new BaseResponse(false, "PayPal is not available now, please contact to our customer service");
    }

    private ItemList buildItemList(final PaymentRequest request) {
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setName(request.getPurpose());
        item.setCurrency("USD");
        item.setPrice(request.getAmount().toString());
        item.setQuantity("1");
        items.add(item);
        itemList.setItems(items);
        return itemList;
    }

    private Amount buildAmount(final PaymentRequest request) {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(request.getAmount().toString());
        return amount;
    }

    private com.paypal.api.payments.Transaction buildTransaction(final ItemList itemList, final Amount amount) {
        com.paypal.api.payments.Transaction transaction = new com.paypal.api.payments.Transaction();
        transaction.setDescription("Thanh toán Sneakery");
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        return transaction;
    }

    private Payment buildPayment(final com.paypal.api.payments.Transaction transaction) {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(Collections.singletonList(transaction));
        return payment;
    }

    private RedirectUrls buildRedirectUrls() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(CLIENT_BASE_URL + "/success");
        redirectUrls.setCancelUrl(CLIENT_BASE_URL + "/cancel");
        return redirectUrls;
    }

    @Override
    @SneakyThrows
    public BaseResponse handleSuccessPayment(final String paymentId, final String payerId, final EPaymentType type, final Long productId) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        Payment executedPayment = payment.execute(apiContext, paymentExecute);
        if (executedPayment.getState().equals("approved")) {
            Long amount = Long.parseLong(StringUtils.removeEnd(executedPayment.getTransactions().get(0).getAmount().getTotal(), ".00"));
            User user = sneakeryUtil.getCurrentUser();
            Bid bid = bidRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException(SneakeryConstant.BID_NOT_FOUND));
            if (type.equals(EPaymentType.AUCTION_FEE) && user.equals(bid.getProduct().getUser())) {
                bid.setSellerPaymentStatus(PaymentStatus.COMPLETED);
            }
            if (type.equals(EPaymentType.PAID) && user.equals(bid.getHolder())) {
                bid.setWinnerPaymentStatus(PaymentStatus.COMPLETED);
            }
            Transaction transaction = Transaction.builder()
                    .amount(amount)
                    .type(type)
                    .bid(bid)
                    .user(user)
                    .build();
            transactionRepository.save(transaction);
            return new BaseResponse(true, "Pay successfully");
        }
        return new BaseResponse(false, "PayPal is not available now, please contact to our customer service");
    }
}

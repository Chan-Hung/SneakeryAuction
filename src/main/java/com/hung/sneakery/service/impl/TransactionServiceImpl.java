package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.request.PaymentRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.*;
import com.hung.sneakery.entity.Transaction;
import com.hung.sneakery.enums.EPaymentType;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.service.TransactionService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TransactionRepository transactionRepository;

    @Resource
    private WalletRepository walletRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private APIContext apiContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    @SneakyThrows
    public Payment createPayment(final PaymentRequest request) {
        ItemList itemList = buildItemList(request);
        Amount amount = buildAmount(request);
        com.paypal.api.payments.Transaction transaction = buildTransaction(itemList, amount);
        Payment payment = buildPayment(transaction);

        RedirectUrls redirectUrls = buildRedirectUrls();
        payment.setRedirectUrls(redirectUrls);

        apiContext.setMaskRequestId(true);
        return payment.create(apiContext);
    }

    private ItemList buildItemList(final PaymentRequest request) {
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setName("Thanh toán Sneakery");
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
        redirectUrls.setReturnUrl("https://sneakery.vercel.app/success");
        redirectUrls.setCancelUrl("https://sneakery.vercel.app/cancel");
        return redirectUrls;
    }

    @Override
    @SneakyThrows
    public Payment executePayment(final String paymentId, final String payerId) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    @Override
    public BaseResponse handleSuccess(final Payment payment, final EPaymentType type) {
        Long amount = Long.parseLong(StringUtils.removeEnd(payment
                .getTransactions().get(0).getAmount().getTotal(), ".00"));
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(type)
                .build();
        transactionRepository.save(transaction);
        return new BaseResponse(true, "Payment successfully");
    }

    @Override
    public Page<Transaction> getByWallet(final Long walletId, final Pageable pageable) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));
        return transactionRepository.findAllByWallet(wallet, pageable);
    }

    @Override
    public BaseResponse paidByWinner(final Long orderId, final Long shippingFee, final Long subtotal) {
        String usernameWinner = SecurityContextHolder.getContext().getAuthentication().getName();
        User winner = userRepository.findByUsername(usernameWinner);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        Bid bid = bidRepository.findById(order.getBid().getId())
                .orElseThrow(() -> new NotFoundException("Bid not found"));
        Long priceWin = order.getBid().getPriceWin();

        List<Wallet> wallets = new ArrayList<>();
        List<Transaction> transactionHistories = new ArrayList<>();

        //WINNER
        //Minus winner's wallet
        Wallet winnerWallet = walletRepository.findByUser_Id(winner.getId());
        winnerWallet.setBalance(winnerWallet.getBalance() - priceWin);
        wallets.add(winnerWallet);

        //Add transaction PAID
        Transaction transaction = Transaction.builder()
                .amount(priceWin)
                .wallet(winnerWallet)
                .type(EPaymentType.PAID)
                .build();
        transactionHistories.add(transaction);

        //SELLER
        //Plus seller's wallet (90%)
        Long sellerReceivedAmount = priceWin * 90L / 100L;
        Wallet sellerWallet = walletRepository.findByUser_Id(bid.getProduct().getUser().getId());
        sellerWallet.setBalance(sellerWallet.getBalance() + sellerReceivedAmount);
        wallets.add(sellerWallet);

        //Add transaction RECEIVED
        Transaction sellerTransaction = Transaction.builder()
                .amount(sellerReceivedAmount)
                .wallet(sellerWallet)
                .type(EPaymentType.RECEIVED)
                .build();
        transactionHistories.add(sellerTransaction);

        //SNEAKERY
        //Plus admin's wallet (10%)
        Long adminReceivedAmount = priceWin * 10L / 100L;
        Wallet adminWallet = walletRepository.findByUser_Id(354L);
        adminWallet.setBalance(adminWallet.getBalance() + adminReceivedAmount);
        wallets.add(adminWallet);

        //Add transaction AUCTION_FEE
        Transaction adminTransaction = Transaction.builder()
                .amount(adminReceivedAmount)
                .wallet(adminWallet)
                .type(EPaymentType.AUCTION_FEE)
                .build();
        transactionHistories.add(adminTransaction);

        order.setShippingFee(shippingFee);
        order.setSubtotal(subtotal);
        orderRepository.save(order);

        walletRepository.saveAll(wallets);
        transactionRepository.saveAll(transactionHistories);

        LOGGER.info("Created order successfully");
        return new BaseResponse(true, "Transaction successfully");
    }

    @Override
    @Transactional
    public BaseResponse withdraw(final Long amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        Wallet userWallet = walletRepository.findByUser_Id(user.getId());
        userWallet.setBalance(userWallet.getBalance() - amount);
        walletRepository.save(userWallet);

        Transaction userTransaction = Transaction.builder()
                .amount(amount)
                .wallet(userWallet)
                .type(EPaymentType.WITHDRAW)
                .build();
        transactionRepository.save(userTransaction);

        return new BaseResponse(true, "Withdraw successfully");
    }
}

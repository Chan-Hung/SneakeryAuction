package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.request.DepositRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.*;
import com.hung.sneakery.enums.EPaymentStatus;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.exception.PayPalTransactionException;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.service.TransactionHistoryService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TransactionHistoryRepository transactionHistoryRepository;

    @Resource
    private WalletRepository walletRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private APIContext apiContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);

    @Override
    public Payment createPayment(final DepositRequest request) throws PayPalRESTException {
        if (Objects.isNull(request.getUserId())) {
            throw new NotFoundException("User not found");
        }
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setName("Nạp tiền cọc Sneakery");
        item.setCurrency("USD");
        item.setPrice(request.getAmount().toString());
        item.setQuantity("1");
        items.add(item);
        itemList.setItems(items);

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(request.getAmount().toString());

        Transaction transaction = new Transaction();
        transaction.setDescription("Nạp tiền cọc Sneakery");
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl("https://sneakery.vercel.app/success");
        redirectUrls.setCancelUrl("https://sneakery.vercel.app/cancel");
        payment.setRedirectUrls(redirectUrls);

        apiContext.setMaskRequestId(true);
        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(final String paymentId, final String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    @Override
    public BaseResponse handleSuccess(final Payment payment) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName);

            Wallet wallet = walletRepository.findByUser_Id(user.getId());
            Long amount = Long.parseLong(StringUtils.removeEnd(payment
                    .getTransactions().get(0).getAmount().getTotal(), ".00"));

            TransactionHistory transactionHistory = TransactionHistory.builder()
                    .amount(amount)
                    .wallet(wallet)
                    .status(EPaymentStatus.DEPOSIT)
                    .build();

            wallet.setBalance(wallet.getBalance() + amount);
            walletRepository.save(wallet);

            transactionHistoryRepository.save(transactionHistory);
        } catch (Exception e) {
            throw new PayPalTransactionException(e.getMessage());
        }
        return new BaseResponse(true, "Payment successfully");
    }

    @Override
    public Page<TransactionHistory> getByWallet(final Long walletId, final Pageable pageable) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));
        return transactionHistoryRepository.findAllByWallet(wallet, pageable);
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
        List<TransactionHistory> transactionHistories = new ArrayList<>();

        //WINNER
        //Minus winner's wallet
        Wallet winnerWallet = walletRepository.findByUser_Id(winner.getId());
        winnerWallet.setBalance(winnerWallet.getBalance() - priceWin);
        wallets.add(winnerWallet);

        //Add transaction PAID
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .amount(priceWin)
                .wallet(winnerWallet)
                .status(EPaymentStatus.PAID)
                .build();
        transactionHistories.add(transactionHistory);

        //SELLER
        //Plus seller's wallet (90%)
        Long sellerReceivedAmount = priceWin * 90L / 100L;
        Wallet sellerWallet = walletRepository.findByUser_Id(bid.getProduct().getUser().getId());
        sellerWallet.setBalance(sellerWallet.getBalance() + sellerReceivedAmount);
        wallets.add(sellerWallet);

        //Add transaction RECEIVED
        TransactionHistory sellerTransactionHistory = TransactionHistory.builder()
                .amount(sellerReceivedAmount)
                .wallet(sellerWallet)
                .status(EPaymentStatus.RECEIVED)
                .build();
        transactionHistories.add(sellerTransactionHistory);

        //SNEAKERY
        //Plus admin's wallet (10%)
        Long adminReceivedAmount = priceWin * 10L / 100L;
        Wallet adminWallet = walletRepository.findByUser_Id(354L);
        adminWallet.setBalance(adminWallet.getBalance() + adminReceivedAmount);
        wallets.add(adminWallet);

        //Add transaction AUCTION_FEE
        TransactionHistory adminTransactionHistory = TransactionHistory.builder()
                .amount(adminReceivedAmount)
                .wallet(adminWallet)
                .status(EPaymentStatus.AUCTION_FEE)
                .build();
        transactionHistories.add(adminTransactionHistory);

        order.setShippingFee(shippingFee);
        order.setSubtotal(subtotal);
        orderRepository.save(order);

        walletRepository.saveAll(wallets);
        transactionHistoryRepository.saveAll(transactionHistories);

        LOGGER.info("Created order successfully");
        return new BaseResponse(true, "Transaction successfully");
    }

    @Override
    public BaseResponse withdraw(final Long amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        Wallet userWallet = walletRepository.findByUser_Id(user.getId());
        userWallet.setBalance(userWallet.getBalance() - amount);
        walletRepository.save(userWallet);

        //Add transaction WITHDRAW
        TransactionHistory userTransactionHistory = TransactionHistory.builder()
                .amount(amount)
                .wallet(userWallet)
                .status(EPaymentStatus.WITHDRAW)
                .build();
        transactionHistoryRepository.save(userTransactionHistory);

        return new BaseResponse(true, "Withdraw successfully");
    }
}

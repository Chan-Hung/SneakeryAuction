package com.hung.sneakery.service.impl;

import com.hung.sneakery.data.models.dto.request.DepositRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.Order;
import com.hung.sneakery.data.models.entities.*;
import com.hung.sneakery.data.remotes.repositories.*;
import com.hung.sneakery.service.TransactionHistoryService;
import com.hung.sneakery.utils.enums.EPaymentStatus;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    @Override
    public Payment createPayment(DepositRequest depositRequest) throws PayPalRESTException {
        if (depositRequest.getUserId() == null)
            throw new RuntimeException("User not found");
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setName("Nạp tiền cọc Sneakery");
        item.setCurrency("USD");
        item.setPrice(depositRequest.getAmount().toString());
        item.setQuantity("1");
        items.add(item);
        itemList.setItems(items);

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(depositRequest.getAmount().toString());

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
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    @Override
    public BaseResponse handleSuccess(Payment payment) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName);

            Wallet wallet = walletRepository.findByUser_Id(user.getId());
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setStatus(EPaymentStatus.DEPOSIT);
            transactionHistory.setWallet(wallet);
            String amount = StringUtils.removeEnd(payment.getTransactions().get(0).getAmount().getTotal(), ".00");
            transactionHistory.setAmount(Long.valueOf(amount));

            Long currentBalance = wallet.getBalance();
            wallet.setBalance(currentBalance + Long.parseLong(amount));
            walletRepository.save(wallet);

            transactionHistoryRepository.save(transactionHistory);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new BaseResponse(true, "Payment successfully");
    }

    @Override
    public DataResponse<List<TransactionHistory>> getAllByWallet() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        Wallet wallet = walletRepository.findByUser_Id(user.getId());
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAllByWallet(wallet);
        return new DataResponse<>(transactionHistoryList);
    }

    @Override
    public BaseResponse paidByWinner(Long orderId, Long shippingFee, Long subtotal) {
        String usernameWinner = SecurityContextHolder.getContext().getAuthentication().getName();
        User winner = userRepository.findByUsername(usernameWinner);

        Order order = orderRepository.findById(orderId).get();
        Bid bid = bidRepository.findById(order.getBid().getId()).get();
        Long priceWin = order.getBid().getPriceWin();

        //WINNER
        //Minus winner's wallet
        Wallet winnerWallet = walletRepository.findByUser_Id(winner.getId());
        winnerWallet.setBalance(winnerWallet.getBalance() - priceWin);
        walletRepository.save(winnerWallet);
        //Add transaction PAID
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setStatus(EPaymentStatus.PAID);
        transactionHistory.setWallet(winnerWallet);
        transactionHistory.setAmount(priceWin);
        transactionHistoryRepository.save(transactionHistory);

        //SELLER
        //Plus seller's wallet (90%)
        Long sellerReceivedAmount = priceWin * 90L / 100L;
        Wallet sellerWallet = walletRepository.findByUser_Id(bid.getProduct().getUser().getId());
        sellerWallet.setBalance(sellerWallet.getBalance() + sellerReceivedAmount);
        walletRepository.save(sellerWallet);
        //Add transaction RECEIVED
        TransactionHistory sellerTransactionHistory = new TransactionHistory();
        sellerTransactionHistory.setStatus(EPaymentStatus.RECEIVED);
        sellerTransactionHistory.setWallet(sellerWallet);
        sellerTransactionHistory.setAmount(sellerReceivedAmount);
        transactionHistoryRepository.save(sellerTransactionHistory);

        //SNEAKERY
        //Plus admin's wallet (10%)
        Long adminReceivedAmount = priceWin * 10L / 100L;
        Wallet adminWallet = walletRepository.findByUser_Id(354L);
        adminWallet.setBalance(adminWallet.getBalance() + adminReceivedAmount);
        walletRepository.save(adminWallet);
        //Add transaction AUCTION_FEE
        TransactionHistory adminTransactionHistory = new TransactionHistory();
        adminTransactionHistory.setStatus(EPaymentStatus.AUCTION_FEE);
        adminTransactionHistory.setWallet(adminWallet);
        adminTransactionHistory.setAmount(adminReceivedAmount);
        transactionHistoryRepository.save(adminTransactionHistory);

        order.setShippingFee(shippingFee);
        order.setSubtotal(subtotal);
        orderRepository.save(order);
        System.out.println("Created order successfully");
        return new BaseResponse(true, "Transaction successfully");
    }

    @Override
    public BaseResponse withdraw(Long amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        Wallet userWallet = walletRepository.findByUser_Id(user.getId());
        userWallet.setBalance(userWallet.getBalance() - amount);
        walletRepository.save(userWallet);

        //Add transaction WITHDRAW
        TransactionHistory userTransactionHistory = new TransactionHistory();
        userTransactionHistory.setStatus(EPaymentStatus.WITHDRAW);
        userTransactionHistory.setWallet(userWallet);
        userTransactionHistory.setAmount(amount);
        transactionHistoryRepository.save(userTransactionHistory);

        return new BaseResponse(true, "Withdraw successfully");
        //
    }
}

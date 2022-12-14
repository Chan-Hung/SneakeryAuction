package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.entities.TransactionHistory;
import com.hung.sneakery.data.models.entities.User;
import com.hung.sneakery.data.models.entities.Wallet;
import com.hung.sneakery.data.remotes.repositories.OrderRepository;
import com.hung.sneakery.data.remotes.repositories.TransactionHistoryRepository;
import com.hung.sneakery.data.remotes.repositories.UserRepository;
import com.hung.sneakery.data.remotes.repositories.WalletRepository;
import com.hung.sneakery.data.remotes.services.TransactionHistoryService;
import com.hung.sneakery.utils.enums.EStatus;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    APIContext apiContext;


    @Override
    public Payment createPayment(Long userId, String cancelUrl, String successUrl) throws PayPalRESTException {
        if (userId == null)
            throw new RuntimeException("Order must not be null !");
        Optional<User> user = userRepository.findById(userId);
//        if (order.getStatus() == EROrderStatus.PAID)
//            throw new CommonRuntimeException("You already paid for this post .!");
//        Post post = order.getPost();
//        Service service = post.getService();

//        Double total = order.getTotal();
//        String currency = order.getCurrency().toString();
//        String method = "paypal";
//        String intent = "sale";
//        String description = String.format("Rent service for post id (%d) with title (%s) in %d months (%d).",
//                post.getId(), post.getTitle(), order.getDuration(), order.getId());
//
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
//
        Item item = new Item();
        item.setName("Nạp tiền vào Sneakery");
        item.setCurrency("USD");
        item.setPrice("88");
        item.setQuantity("1");
        items.add(item);
        itemList.setItems(items);

        Amount amount = new Amount();
        amount.setCurrency("USD");
//        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal("88");

        Transaction transaction = new Transaction();
        transaction.setDescription(null);
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
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

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
    public BaseResponse handleSuccess(Payment payment, Long userId) {
        try {
            Wallet wallet = walletRepository.findByUser_Id(userId);
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setStatus(EStatus.DEPOSIT);
            transactionHistory.setWallet(wallet);
            Long amount = Long.valueOf(payment.getTransactions().get(0).getAmount().getTotal().substring(0, 2));
            transactionHistory.setAmount(amount);

            Long currentBalance = wallet.getBalance();
            wallet.setBalance(currentBalance+amount);
            walletRepository.save(wallet);

//            Long postId = (long) lstNum.get(0);
//            Long orderId = (long) lstNum.get(lstNum.size() - 1);
//
//            Post post = postRepo.findById(postId).get();
//            PostOrder order = orderRepo.findById(orderId).get();
//
//            post.setStatus(EStatus.WAIT_FOR_ACCEPT);
//
//            order.setPaidDate(new Date());
//            order.setStatus(EROrderStatus.PAID);
//            order.setPost(post);
//
//            orderRepo.save(order);
            transactionHistoryRepository.save(transactionHistory);
        } catch (Exception e) {
            return new BaseResponse(false, e.getMessage());

        }
        return new BaseResponse(true, "Payment successfully");


    }
}

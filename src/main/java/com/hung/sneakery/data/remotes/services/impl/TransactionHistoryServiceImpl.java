package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.request.DepositRequest;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Payment createPayment(DepositRequest depositRequest) throws PayPalRESTException {
        if (depositRequest.getUserId() == null)
            throw new RuntimeException("User not found");
        Optional<User> user = userRepository.findById(depositRequest.getUserId());

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
//
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl("http://localhost:3000/success");
        redirectUrls.setCancelUrl("http://localhost:3000/cancel");

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
    public BaseResponse handleSuccess(Payment payment) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName);

            Wallet wallet = walletRepository.findByUser_Id(user.getId());
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setStatus(EStatus.DEPOSIT);
            transactionHistory.setWallet(wallet);
            String amount = StringUtils.removeEnd(payment.getTransactions().get(0).getAmount().getTotal(),".00");
            transactionHistory.setAmount(Long.valueOf(amount));

            Long currentBalance = wallet.getBalance();
            wallet.setBalance(currentBalance+Long.parseLong(amount));
            walletRepository.save(wallet);

            transactionHistoryRepository.save(transactionHistory);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new BaseResponse(true, "Payment successfully");


    }
}

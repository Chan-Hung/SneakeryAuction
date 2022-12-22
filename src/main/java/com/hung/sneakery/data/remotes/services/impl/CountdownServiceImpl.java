package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.entities.*;
import com.hung.sneakery.data.remotes.repositories.*;
import com.hung.sneakery.data.remotes.services.CountdownService;
import com.hung.sneakery.utils.enums.EPaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class CountdownServiceImpl implements CountdownService {
    @Autowired
    BidHistoryRepository bidHistoryRepository;
    @Autowired
    BidRepository bidRepository;

    @Autowired
    ShippingFeeRepository shippingFeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timer timer = new Timer();

    @Override
    public void biddingCountdown(Bid bid) {
        System.out.println("Current Time Execute: " + df.format(new Date()));
        //DateTime when executing
        Date date = Date.from(bid.getBidClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(new CountdownTask(bid, bidHistoryRepository, bidRepository, shippingFeeRepository, userRepository, addressRepository, orderRepository, walletRepository, transactionHistoryRepository), date);
        System.out.println("Current Time Schedule: " + df.format(new Date()));

    }

    private static class CountdownTask extends TimerTask {
        BidHistoryRepository bidHistoryRepository;

        BidRepository bidRepository;

        ShippingFeeRepository shippingFeeRepository;

        UserRepository userRepository;

        AddressRepository addressRepository;

        OrderRepository orderRepository;

        WalletRepository walletRepository;

        TransactionHistoryRepository transactionHistoryRepository;

        Bid bid;

        @Override
        public void run() {
            Tuple winnerTuple = bidHistoryRepository.getWinner(bid.getId());
            if (winnerTuple == null) {
                //Bid ends without winner
                bid.setPriceWin(0L);
                bidRepository.save(bid);
                System.out.println("Time Schedule Set Price Win = 0: " + LocalDateTime.now());
            } else {
                System.out.println("Time Schedule Set Price Win <> 0: " + LocalDateTime.now());
                BigInteger priceWin = winnerTuple.get("priceWin", BigInteger.class);
                BigInteger userId = winnerTuple.get("buyerId", BigInteger.class);
                bid.setPriceWin(priceWin.longValue());//End bid totally
                bidRepository.save(bid);
                System.out.println("Update Price Win successfully");
                System.out.println("Price win: " + priceWin);
                Bid bid1 = bidRepository.findById(bid.getId()).get();
                //Create Order
                Order order = new Order();
                order.setBid(bid1);
                order.setStatus("SUCCESS");

                //Set order's seller
                User seller = bid.getProduct().getUser();
                order.setSeller(seller);
                order.setCreatedAt(LocalDateTime.now());

                //Set order's winner
                Long userIdWin = userId.longValue();
                User winner = userRepository.findById(userIdWin).get();
                order.setWinner(winner);

                //Set order's shipping fee
                Address sellerAddress = addressRepository.findAddressByUser(seller);
                Address winnerAddress = addressRepository.findAddressByUser(winner);
                ShippingFee shippingFee = shippingFeeRepository.findShippingFeeByOriginAndDestination(sellerAddress.getDistrict().getName(), winnerAddress.getDistrict().getName());
                order.setShippingFee(shippingFee);

                //WINNER
                //Minus winner's wallet
                Long winnerPaidAmount = priceWin.longValue();
                Wallet winnerWallet = walletRepository.findByUser_Id(userIdWin);
                winnerWallet.setBalance(winnerWallet.getBalance() - winnerPaidAmount);
                walletRepository.save(winnerWallet);
                //Add transaction PAID
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.setStatus(EPaymentStatus.PAID);
                transactionHistory.setWallet(winnerWallet);
                transactionHistory.setAmount(winnerPaidAmount);
                transactionHistoryRepository.save(transactionHistory);

                //SELLER
                //Plus seller's wallet (90%)
                Long sellerReceivedAmount = priceWin.longValue() * 90L / 100L;
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
                Long adminReceivedAmount = priceWin.longValue() * 10L / 100L;
                Wallet adminWallet = walletRepository.findByUser_Id(354L);
                adminWallet.setBalance(adminWallet.getBalance() + adminReceivedAmount);
                walletRepository.save(adminWallet);
                //Add transaction AUCTION_FEE
                TransactionHistory adminTransactionHistory = new TransactionHistory();
                adminTransactionHistory.setStatus(EPaymentStatus.AUCTION_FEE);
                adminTransactionHistory.setWallet(adminWallet);
                adminTransactionHistory.setAmount(adminReceivedAmount);
                transactionHistoryRepository.save(adminTransactionHistory);

                orderRepository.save(order);
                System.out.println("Created order successfully");
            }
        }

        //Constructor
        public CountdownTask(Bid bid, BidHistoryRepository bidHistoryRepository, BidRepository bidRepository, ShippingFeeRepository shippingFeeRepository, UserRepository userRepository, AddressRepository addressRepository, OrderRepository orderRepository, WalletRepository walletRepository, TransactionHistoryRepository transactionHistoryRepository) {
            this.bid = bid;
            this.bidHistoryRepository = bidHistoryRepository;
            this.bidRepository = bidRepository;
            this.shippingFeeRepository = shippingFeeRepository;
            this.userRepository = userRepository;
            this.addressRepository = addressRepository;
            this.orderRepository = orderRepository;
            this.walletRepository = walletRepository;
            this.transactionHistoryRepository = transactionHistoryRepository;
        }
    }
}

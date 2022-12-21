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
        System.out.println("Current Time Execute: " + df.format( new Date()));
        //DateTime when executing
        Date date = Date.from(bid.getBidClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(new CountdownTask(bid, bidHistoryRepository, bidRepository,shippingFeeRepository, userRepository, addressRepository, orderRepository, walletRepository, transactionHistoryRepository), date);
        System.out.println("Current Time Schedule: " + df.format( new Date()));

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
            if (winnerTuple == null){
                bid.setPriceWin(0L);
                bidRepository.save(bid);
                System.out.println("Time Schedule Set Price Win = 0: " + LocalDateTime.now());
            }
                //Bid ends without winner
            else {
                System.out.println("Time Schedule Set Price Win ...: " + LocalDateTime.now());
                BigInteger priceWin = winnerTuple.get("priceWin", BigInteger.class);
                BigInteger userId = winnerTuple.get("buyerId", BigInteger.class);
                bid.setPriceWin(priceWin.longValue());//End bid totally
                bidRepository.save(bid);
                System.out.println("Update priceWin successfully");

                Bid bid1  = bidRepository.findById(bid.getId()).get();
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
                ShippingFee shippingFee = shippingFeeRepository.findShippingFeeByOriginAndDestination(sellerAddress.getDistrict().getName(),winnerAddress.getDistrict().getName());
                order.setShippingFee(shippingFee);

                //Minus winner's wallet
                Wallet wallet = walletRepository.findByUser_Id(userIdWin);
                wallet.setBalance(wallet.getBalance() - priceWin.longValue());
                walletRepository.save(wallet);

                //Add transaction PAID
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.setStatus(EPaymentStatus.PAID);
                transactionHistory.setWallet(wallet);
                transactionHistoryRepository.save(transactionHistory);

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

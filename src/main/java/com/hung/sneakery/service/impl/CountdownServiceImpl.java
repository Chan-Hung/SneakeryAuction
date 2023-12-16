package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.service.CountdownService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class CountdownServiceImpl implements CountdownService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountdownServiceImpl.class);

    @Resource
    private BidHistoryRepository bidHistoryRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private ShippingFeeRepository shippingFeeRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private WalletRepository walletRepository;

    @Resource
    private TransactionHistoryRepository transactionHistoryRepository;

    Timer timer = new Timer();

    @Override
    public void biddingCountdown(Bid bid) {
        LOGGER.info("------------------------CURRENT TIME EXECUTE------------------------");
        //DateTime when executing
        Date date = Date.from(bid.getBidClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(new CountdownTask(bid, bidHistoryRepository, bidRepository, shippingFeeRepository, userRepository, orderRepository, walletRepository, transactionHistoryRepository), date);
        LOGGER.info("------------------------CURRENT TIME SCHEDULE------------------------");
    }

    private static class CountdownTask extends TimerTask {
        BidHistoryRepository bidHistoryRepository;

        BidRepository bidRepository;

        ShippingFeeRepository shippingFeeRepository;

        UserRepository userRepository;

        OrderRepository orderRepository;

        WalletRepository walletRepository;

        TransactionHistoryRepository transactionHistoryRepository;

        Bid bid;

        @Override
        public void run() {
            Tuple winnerTuple = bidHistoryRepository.getWinner(bid.getId());
            //Bid ends without winner
            if (Objects.isNull(winnerTuple)) {
                bid.setPriceWin(0L);
                bidRepository.save(bid);
                LOGGER.info("------------------------TIME SCHEDULE SET PRICE WIN = 0 FOR PRODUCT: {}------------------------", bid.getId());
            } else {
                LOGGER.info("------------------------TIME SCHEDULE SET PRICE WIN <> 0 FOR PRODUCT: {}------------------------", bid.getId());
                BigInteger priceWin = winnerTuple.get("priceWin", BigInteger.class);
                BigInteger userId = winnerTuple.get("buyerId", BigInteger.class);
                bid.setPriceWin(priceWin.longValue());//End bid totally
                bidRepository.save(bid);
                LOGGER.info("------------------------UPDATE PRICE WIN {} SUCCESSFULLY------------------------", priceWin);
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
//                Address sellerAddress = addressRepository.findAddressByUser(seller);
//                Address winnerAddress = addressRepository.findAddressByUser(winner);
//                ShippingFee shippingFee = shippingFeeRepository.findShippingFeeByOriginAndDestination(sellerAddress.getDistrict().getName(), winnerAddress.getDistrict().getName());
//                order.setShippingFee(100L);

                //WINNER
                //Minus winner's wallet
//                Long winnerPaidAmount = priceWin.longValue();
//                Wallet winnerWallet = walletRepository.findByUser_Id(userIdWin);
//                winnerWallet.setBalance(winnerWallet.getBalance() - winnerPaidAmount);
//                walletRepository.save(winnerWallet);
//                //Add transaction PAID
//                TransactionHistory transactionHistory = new TransactionHistory();
//                transactionHistory.setStatus(EPaymentStatus.PAID);
//                transactionHistory.setWallet(winnerWallet);
//                transactionHistory.setAmount(winnerPaidAmount);
//                transactionHistoryRepository.save(transactionHistory);
//
//                //SELLER
//                //Plus seller's wallet (90%)
//                Long sellerReceivedAmount = priceWin.longValue() * 90L / 100L;
//                Wallet sellerWallet = walletRepository.findByUser_Id(bid.getProduct().getUser().getId());
//                sellerWallet.setBalance(sellerWallet.getBalance() + sellerReceivedAmount);
//                walletRepository.save(sellerWallet);
//                //Add transaction RECEIVED
//                TransactionHistory sellerTransactionHistory = new TransactionHistory();
//                sellerTransactionHistory.setStatus(EPaymentStatus.RECEIVED);
//                sellerTransactionHistory.setWallet(sellerWallet);
//                sellerTransactionHistory.setAmount(sellerReceivedAmount);
//                transactionHistoryRepository.save(sellerTransactionHistory);
//
//                //SNEAKERY
//                //Plus admin's wallet (10%)
//                Long adminReceivedAmount = priceWin.longValue() * 10L / 100L;
//                Wallet adminWallet = walletRepository.findByUser_Id(354L);
//                adminWallet.setBalance(adminWallet.getBalance() + adminReceivedAmount);
//                walletRepository.save(adminWallet);
//                //Add transaction AUCTION_FEE
//                TransactionHistory adminTransactionHistory = new TransactionHistory();
//                adminTransactionHistory.setStatus(EPaymentStatus.AUCTION_FEE);
//                adminTransactionHistory.setWallet(adminWallet);
//                adminTransactionHistory.setAmount(adminReceivedAmount);
//                transactionHistoryRepository.save(adminTransactionHistory);

                orderRepository.save(order);
                LOGGER.info("Created order successfully");
            }
        }

        //Constructor
        public CountdownTask(Bid bid, BidHistoryRepository bidHistoryRepository, BidRepository bidRepository, ShippingFeeRepository shippingFeeRepository, UserRepository userRepository, OrderRepository orderRepository, WalletRepository walletRepository, TransactionHistoryRepository transactionHistoryRepository) {
            this.bid = bid;
            this.bidHistoryRepository = bidHistoryRepository;
            this.bidRepository = bidRepository;
            this.shippingFeeRepository = shippingFeeRepository;
            this.userRepository = userRepository;
            this.orderRepository = orderRepository;
            this.walletRepository = walletRepository;
            this.transactionHistoryRepository = transactionHistoryRepository;
        }
    }
}

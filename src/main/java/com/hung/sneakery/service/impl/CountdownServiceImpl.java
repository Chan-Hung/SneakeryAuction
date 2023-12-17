package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EOrderStatus;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.service.CountdownService;
import lombok.AllArgsConstructor;
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
        LOGGER.info("---CURRENT TIME EXECUTE---");
        //DateTime when executing
        Date date = Date.from(bid.getBidClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(new CountdownTask(bid, bidHistoryRepository, bidRepository, userRepository, orderRepository, walletRepository, transactionHistoryRepository), date);
        LOGGER.info("---CURRENT TIME SCHEDULE---");
    }

    @AllArgsConstructor
    private static class CountdownTask extends TimerTask {
        Bid bid;

        BidHistoryRepository bidHistoryRepository;

        BidRepository bidRepository;

        UserRepository userRepository;

        OrderRepository orderRepository;

        WalletRepository walletRepository;

        TransactionHistoryRepository transactionHistoryRepository;

        @Override
        public void run() {
            Tuple winnerTuple = bidHistoryRepository.getWinner(bid.getId());
            //Bid ends without winner
            if (Objects.isNull(winnerTuple)) {
                bid.setPriceWin(0L);
                bidRepository.save(bid);
                LOGGER.info("---TIME SCHEDULE SET PRICE WIN = 0 FOR PRODUCT: {}---", bid.getId());
            } else {
                LOGGER.info("---TIME SCHEDULE SET PRICE WIN <> 0 FOR PRODUCT: {}---", bid.getId());
                BigInteger priceWin = winnerTuple.get("priceWin", BigInteger.class);
                BigInteger userId = winnerTuple.get("buyerId", BigInteger.class);
                bid.setPriceWin(priceWin.longValue());
                bidRepository.save(bid);
                LOGGER.info("---UPDATE PRICE WIN {} SUCCESSFULLY---", priceWin);
                Bid bid1 = bidRepository.findById(bid.getId())
                        .orElseThrow(() -> new NotFoundException("Bid not found"));
                //Create Order
                Order order = new Order();
                order.setBid(bid1);
                order.setStatus(EOrderStatus.PENDING);

                //Set order's seller
                User seller = bid.getProduct().getUser();
                order.setSeller(seller);

                //Set order's winner
                Long userIdWin = userId.longValue();
                User winner = userRepository.findById(userIdWin)
                        .orElseThrow(() -> new NotFoundException("Winner not found"));
                order.setWinner(winner);
                orderRepository.save(order);
                LOGGER.info("---Created order successfully---");
            }
        }
    }
}

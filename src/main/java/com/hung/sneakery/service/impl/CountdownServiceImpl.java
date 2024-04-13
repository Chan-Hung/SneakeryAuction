package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EOrderStatus;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.BidHistoryRepository;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.repository.OrderRepository;
import com.hung.sneakery.repository.UserRepository;
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

    private final Timer timer = new Timer();

    @Override
    public void biddingCountdown(Bid bid) {
        LOGGER.info("---CURRENT TIME EXECUTE: {}", LocalDateTime.now());
        Date closingDate = Date.from(bid.getClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(new CountdownTask(bid, this), closingDate);
        LOGGER.info("---CURRENT TIME SCHEDULE: {}", closingDate);
    }

    private static class CountdownTask extends TimerTask {
        private final Bid bid;
        private final CountdownServiceImpl countdownService;

        CountdownTask(Bid bid, CountdownServiceImpl countdownService) {
            this.bid = bid;
            this.countdownService = countdownService;
        }

        @Override
        public void run() {
            countdownService.handleBidCompletion(bid);
        }
    }

    private void handleBidCompletion(Bid bid) {
        Tuple winnerTuple = bidHistoryRepository.getWinner(bid.getId());
        if (winnerTuple == null) {
            LOGGER.info("---TIME SCHEDULE SET PRICE WIN = 0 FOR PRODUCT: {}---", bid.getId());
            setPriceWinAndSaveBid(bid, 0L);
        } else {
            LOGGER.info("---TIME SCHEDULE SET PRICE WIN <> 0 FOR PRODUCT: {}---", bid.getId());
            handleWinnerBid(bid, winnerTuple);
        }
    }

    private void handleWinnerBid(Bid bid, Tuple winnerTuple) {
        BigInteger priceWin = winnerTuple.get("priceWin", BigInteger.class);
        BigInteger userId = winnerTuple.get("buyerId", BigInteger.class);
        if (bid.getReservePrice() != null && priceWin.compareTo(BigInteger.valueOf(bid.getReservePrice())) < 0) {
            LOGGER.info("---PRICE WIN: {} < RESERVE PRICE: {} FOR BID {}---", priceWin, bid.getReservePrice(), bid.getId());
            setPriceWinAndSaveBid(bid, 0L);
            return;
        }
        setPriceWinAndSaveBid(bid, priceWin.longValue());
        createAndSaveOrder(bid, userId.longValue());
        LOGGER.info("---Created order successfully---");
    }

    private void setPriceWinAndSaveBid(Bid bid, Long priceWin) {
        bid.setPriceWin(priceWin);
        bidRepository.save(bid);
        LOGGER.info("---UPDATE PRICE WIN {} SUCCESSFULLY---", priceWin);
    }

    private void createAndSaveOrder(Bid bid, Long userId) {
        Bid savedBid = bidRepository.findById(bid.getId())
                .orElseThrow(() -> new NotFoundException("Bid not found"));

        Order order = new Order();
        order.setBid(savedBid);
        order.setStatus(EOrderStatus.PENDING);

        User seller = savedBid.getProduct().getUser();
        order.setSeller(seller);

        User winner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Winner not found"));
        order.setWinner(winner);

        orderRepository.save(order);
    }
}

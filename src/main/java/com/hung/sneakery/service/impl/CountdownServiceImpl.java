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
import com.hung.sneakery.service.MailService;
import lombok.SneakyThrows;
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
    private static final String EMAIL_SUBJECT = "Kết quả phiên đấu giá";
    private static final String EMAIL_TEMPLATE_PATH = "classpath:email-templates/reserve-price-notification.html";
    @Resource
    private BidHistoryRepository bidHistoryRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private MailService mailService;

    private final Timer timer = new Timer();
    private TimerTask currentCountdownTask;

    @Override
    public void biddingCountdown(final Bid bid) {
        LOGGER.info("---CURRENT TIME EXECUTE: {}", LocalDateTime.now());

        cancelPreviousCountdownTask();

        Date closingDate = Date.from(bid.getClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        currentCountdownTask = new CountdownTask(bid, this);

        timer.schedule(currentCountdownTask, closingDate);
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

    private void cancelPreviousCountdownTask() {
        if (currentCountdownTask != null) {
            currentCountdownTask.cancel();
            LOGGER.info("Previous countdown task canceled.");
        }
    }

    private void handleBidCompletion(final Bid bid) {
        Tuple winnerTuple = bidHistoryRepository.getWinner(bid.getId());
        if (winnerTuple == null) {
            LOGGER.info("---TIME SCHEDULE SET PRICE WIN = 0 FOR PRODUCT: {}---", bid.getProduct().getName());
            setPriceWinAndSaveBid(bid, 0L);
        } else {
            LOGGER.info("---TIME SCHEDULE SET PRICE WIN <> 0 FOR PRODUCT: {}---", bid.getProduct().getName());
            handleWinnerBid(bid, winnerTuple);
        }
    }

    @SneakyThrows
    private void handleWinnerUnderReservePrice(Bid bid, final Long priceWin, final User winner) {
        LOGGER.info("---PRICE WIN: {} < RESERVE PRICE: {} FOR PRODUCT {}---", priceWin, bid.getReservePrice(), bid.getProduct().getName());

        // Send email to notify winner not reach to reserve price
        mailService.sendEmail(EMAIL_SUBJECT, EMAIL_TEMPLATE_PATH, winner, bid.getProduct());
        setPriceWinAndSaveBid(bid, 0L);
    }

    private void handleWinnerBid(final Bid bid, final Tuple winnerTuple) {
        Long priceWin = winnerTuple.get("priceWin", BigInteger.class).longValue();
        Long userId = winnerTuple.get("buyerId", BigInteger.class).longValue();
        User winner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Winner not found"));
        if (bid.getReservePrice() != null && priceWin < bid.getReservePrice()) {
            handleWinnerUnderReservePrice(bid, priceWin, winner);
        }
        setPriceWinAndSaveBid(bid, priceWin);
        createAndSaveOrder(bid, winner);
        LOGGER.info("---Created order successfully---");
    }

    private void setPriceWinAndSaveBid(final Bid bid, final Long priceWin) {
        bid.setPriceWin(priceWin);
        bidRepository.save(bid);
        LOGGER.info("---UPDATE PRICE WIN {} FOR PRODUCT {} SUCCESSFULLY---", priceWin, bid.getProduct().getName());
    }

    private void createAndSaveOrder(final Bid bid, final User winner) {
        Order order = new Order();
        order.setBid(bid);
        order.setStatus(EOrderStatus.PENDING);

        User seller = bid.getProduct().getUser();
        order.setSeller(seller);
        order.setWinner(winner);

        orderRepository.save(order);
    }
}

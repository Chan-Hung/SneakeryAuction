package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.Order;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.enums.EOrderStatus;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.repository.OrderRepository;
import com.hung.sneakery.service.CountdownService;
import com.hung.sneakery.service.MailService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class CountdownServiceImpl implements CountdownService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountdownServiceImpl.class);
    private static final String EMAIL_SUBJECT = "Kết quả phiên đấu giá";
    private static final String EMAIL_TEMPLATE_PATH = "classpath:email-templates/reserve-price-notification.html";

    @Resource
    private BidRepository bidRepository;

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
        BidHistory highestBid = bid.getBidHistories()
                .stream()
                .max(Comparator.comparing(BidHistory::getActualPrice))
                .orElse(null);
        if (highestBid == null) {
            LOGGER.info("---TIME SCHEDULE SET PRICE WIN = 0 FOR PRODUCT: {}---", bid.getProduct().getName());
            setPriceWinAndSaveBid(bid, 0L);
        } else {
            LOGGER.info("---TIME SCHEDULE SET PRICE WIN <> 0 FOR PRODUCT: {}---", bid.getProduct().getName());
            handleWinnerBid(bid, highestBid);
        }
    }

    @SneakyThrows
    private void handleWinnerUnderReservePrice(final Bid bid, final BidHistory highestBid) {
        LOGGER.info("---PRICE WIN: {} < RESERVE PRICE: {} FOR PRODUCT {}---", highestBid.getActualPrice(), bid.getReservePrice(), bid.getProduct().getName());

        // Send email to notify winner not reach to reserve price
        mailService.sendEmail(EMAIL_SUBJECT, EMAIL_TEMPLATE_PATH, bid.getHolder(), bid.getProduct(), null);
        setPriceWinAndSaveBid(bid, 0L);
    }

    private void handleWinnerBid(final Bid bid, final BidHistory highestBid) {
        if (bid.getReservePrice() != null && highestBid.getActualPrice() < bid.getReservePrice()) {
            handleWinnerUnderReservePrice(bid, highestBid);
        }
        setPriceWinAndSaveBid(bid, highestBid.getActualPrice());
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

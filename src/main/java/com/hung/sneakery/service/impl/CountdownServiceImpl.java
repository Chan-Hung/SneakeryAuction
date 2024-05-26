package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.enums.BidOutcome;
import com.hung.sneakery.enums.PaymentStatus;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.service.CountdownService;
import com.hung.sneakery.service.MailService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class CountdownServiceImpl implements CountdownService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountdownServiceImpl.class);
    private static final String EMAIL_SUBJECT = "Kết quả phiên đấu giá";
    private static final String EMAIL_TEMPLATE_PATH = "classpath:email-templates/reserve-price-notification.html";

    @Resource
    private BidRepository bidRepository;

    @Resource
    private MailService mailService;

    private Timer timer = new Timer();
    private TimerTask currentCountdownTask;
    private boolean isTimerCancelled = false;

    @Override
    @Transactional
    public void biddingCountdown(final Bid bid) {
        LOGGER.info("---CURRENT TIME EXECUTE: {}", LocalDateTime.now());
        Bid managedBid = bidRepository.findById(bid.getId())
                .orElseThrow(() -> new NotFoundException("Bid not found"));
        cancelPreviousCountdownTask();
        Date closingDate = Date.from(managedBid.getClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        currentCountdownTask = new CountdownTask(managedBid, this);

        // Reinitialize the timer if it has been cancelled
        if (isTimerCancelled) {
            timer = new Timer();
            isTimerCancelled = false;
        }
        timer.schedule(currentCountdownTask, closingDate);
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

    //Ensured methods modifying the timer or task are synchronized
    // to avoid race conditions and ensure thread safety.
    private synchronized void cancelPreviousCountdownTask() {
        if (currentCountdownTask != null) {
            currentCountdownTask.cancel();
            isTimerCancelled = true;
            LOGGER.info("Previous countdown task canceled.");
        }
    }

    private void handleBidCompletion(final Bid bid) {
        Set<BidHistory> bidHistories = bid.getBidHistories();
        if (bidHistories == null) {
            LOGGER.error("Bid histories are null for bid: {}", bid);
            return;
        }

        BidHistory highestBid = bid.getBidHistories()
                .stream()
                .max(Comparator.comparing(BidHistory::getActualPrice))
                .orElse(null);
        if (highestBid == null) {
            LOGGER.info("---TIME SCHEDULE SET PRICE WIN = 0 FOR PRODUCT: {}---", bid.getProduct().getName());
            setPriceWinAndSaveBid(bid, 0L, BidOutcome.CLOSED_WITHOUT_WINNER);
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
        setPriceWinAndSaveBid(bid, 0L, BidOutcome.CLOSED_WITHOUT_WINNER);
    }

    private void handleWinnerBid(final Bid bid, final BidHistory highestBid) {
        if (bid.getReservePrice() != null && highestBid.getActualPrice() < bid.getReservePrice()) {
            handleWinnerUnderReservePrice(bid, highestBid);
        }
        setPriceWinAndSaveBid(bid, highestBid.getActualPrice(), BidOutcome.CLOSED);
        LOGGER.info("---Created order successfully---");
    }

    private void setPriceWinAndSaveBid(final Bid bid, final Long priceWin, final BidOutcome bidOutcome) {
        bid.setPriceWin(priceWin);
        bid.setBidOutcome(bidOutcome);
        if (!BidOutcome.CLOSED_WITHOUT_WINNER.equals(bidOutcome)) {
            bid.setWinnerPaymentStatus(PaymentStatus.PENDING);
            bid.setSellerPaymentStatus(PaymentStatus.PENDING);
        }
        bidRepository.save(bid);
        LOGGER.info("---UPDATE PRICE WIN {} FOR PRODUCT {} SUCCESSFULLY---", priceWin, bid.getProduct().getName());
    }
}

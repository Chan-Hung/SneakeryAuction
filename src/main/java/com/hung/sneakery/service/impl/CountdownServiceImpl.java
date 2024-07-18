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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class CountdownServiceImpl implements CountdownService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountdownServiceImpl.class);
    private static final String EMAIL_SUBJECT = "Kết quả phiên đấu giá";
    private static final String EMAIL_TEMPLATE_PATH = "email-templates/reserve-price-notification.html";

    private static final String EMAIL_TEMPLATE_PATH_WINNER = "email-templates/winner-notification.html";

    @Resource
    private BidRepository bidRepository;

    @Resource
    private MailService mailService;

    private Timer timer = new Timer();
    private TimerTask currentCountdownTask;
    private boolean isTimerCancelled = false;

    @Override
    public void biddingCountdown(final Bid bid) {
        LOGGER.info("---CURRENT TIME EXECUTE: {}", LocalDateTime.now());
        cancelPreviousCountdownTask();
        Date closingDate = Date.from(bid.getClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        currentCountdownTask = new CountdownTask(bid, this);

        // Reinitialize the timer if it has been cancelled
        if (isTimerCancelled) {
            LOGGER.info("Reinitialize the timer.");
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
        LOGGER.info("TIME SCHEDULE HANDLE BID COMPLETION FOR PRODUCT: {}", bid.getProduct().getName());
        Bid managedBid = bidRepository.findById(bid.getId()).orElseThrow(() -> new NotFoundException("Bid not found"));
        BidHistory highestBid = managedBid.getBidHistories().stream()
                .max(Comparator.comparing(BidHistory::getMaxPrice))
                .orElse(null);
        if (Objects.isNull(highestBid)) {
            LOGGER.info("TIME SCHEDULE SET PRICE WIN = 0 FOR PRODUCT: {}", bid.getProduct().getName());
            setPriceWinAndSaveBid(bid, null, BidOutcome.CLOSED_WITHOUT_WINNER);
        } else {
            LOGGER.info("TIME SCHEDULE SET PRICE WIN <> 0 FOR PRODUCT: {}", bid.getProduct().getName());
            handleWinnerBid(bid, highestBid);
        }
    }

    @SneakyThrows
    private void handleWinnerUnderReservePrice(final Bid bid, final BidHistory highestBid) {
        LOGGER.info("PRICE WIN: {} < RESERVE PRICE: {} FOR PRODUCT {}",
                bid.getPriceWin(),
                bid.getReservePrice(), bid.getProduct().getName());

        mailService.sendEmail(EMAIL_SUBJECT, EMAIL_TEMPLATE_PATH, bid.getHolder(), bid.getProduct(), null);
        setPriceWinAndSaveBid(bid, highestBid, BidOutcome.CLOSED_WITHOUT_WINNER);
    }

    @SneakyThrows
    private void handleWinnerBid(final Bid bid, final BidHistory highestBid) {
        Bid winnerBid = bidRepository.findById(bid.getId())
                .orElseThrow(() -> new NotFoundException("Bid not found"));
        LOGGER.info("PRICE WIN: {} >= RESERVE PRICE: {} FOR PRODUCT {}",
                winnerBid.getPriceWin(),
                bid.getReservePrice(), bid.getProduct().getName());
        if (bid.getReservePrice() != null && winnerBid.getPriceWin() < bid.getReservePrice()) {
            handleWinnerUnderReservePrice(bid, highestBid);
        }
        mailService.sendEmail(EMAIL_SUBJECT, EMAIL_TEMPLATE_PATH_WINNER, bid.getHolder(), bid.getProduct(), winnerBid.getPriceWin());
        setPriceWinAndSaveBid(bid, highestBid, BidOutcome.CLOSED);
    }

    private void setPriceWinAndSaveBid(final Bid bid, final BidHistory bidHistory, final BidOutcome bidOutcome) {
        bid.setBidOutcome(bidOutcome);
        if (!BidOutcome.CLOSED_WITHOUT_WINNER.equals(bidOutcome) && Objects.nonNull(bidHistory)) {
            bid.setWinnerPaymentStatus(PaymentStatus.PENDING);
            bid.setSellerPaymentStatus(PaymentStatus.PENDING);
            bid.setHolder(bidHistory.getUser());
            LOGGER.info("Winner {}", bid.getHolder());
        }
        bidRepository.save(bid);
    }
}

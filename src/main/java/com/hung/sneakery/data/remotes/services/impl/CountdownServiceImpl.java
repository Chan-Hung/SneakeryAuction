package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.entities.Bid;
import com.hung.sneakery.data.remotes.repositories.BidHistoryRepository;
import com.hung.sneakery.data.remotes.repositories.BidRepository;
import com.hung.sneakery.data.remotes.services.CountdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timer timer = new Timer();

    @Override
    public void biddingCountdown(Bid bid) {
        System.out.println("Current Time Execute: " + df.format( new Date()));
        //DateTime when executing
        Date date = Date.from(bid.getBidClosingDateTime().atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(new CountdownTask(bid, bidHistoryRepository, bidRepository), date);
    }

    private static class CountdownTask extends TimerTask {
        BidHistoryRepository bidHistoryRepository;
        BidRepository bidRepository;
        Bid bid;
        @Override
        public void run() {
            Tuple winner = bidHistoryRepository.getWinner(bid.getId());
            BigInteger priceWin = winner.get("priceWin", BigInteger.class);

            bid.setPriceWin(priceWin.longValue());
            bidRepository.save(bid);
            System.out.println("Update priceWin successfully");
        }

        //Constructor
        private CountdownTask(Bid bid, BidHistoryRepository bidHistoryRepository, BidRepository bidRepository) {
            this.bid = bid;
            this.bidHistoryRepository = bidHistoryRepository;
            this.bidRepository = bidRepository;
        }
    }
}

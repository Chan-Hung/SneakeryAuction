package com.hung.sneakery.service;

import com.hung.sneakery.entity.Bid;

public interface CountdownService {

    /**
     * Count down
     *
     * @param bid Bid
     */
    void biddingCountdown(Bid bid);
}

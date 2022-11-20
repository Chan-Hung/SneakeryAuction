package com.hung.sneakery.services.impl;

import com.hung.sneakery.model.Bid;
import com.hung.sneakery.model.BidHistory;
import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.User;
import com.hung.sneakery.payload.request.BidPlaceRequest;
import com.hung.sneakery.payload.response.DataResponse;
import com.hung.sneakery.repository.BidHistoryRepository;
import com.hung.sneakery.repository.BidRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.repository.UserRepository;
import com.hung.sneakery.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    BidHistoryRepository bidHistoryRepository;

    @Override
    public DataResponse<BidHistory> placeBid(BidPlaceRequest bidPlaceRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByUsername(userName).get();

        Product product = productRepository.findById(bidPlaceRequest.getProductId()).get();
        Bid bid = bidRepository.findById(bidPlaceRequest.getProductId()).get();
        Long amount = bidPlaceRequest.getAmount();
        Long stepBid = bid.getStepBid();
        //Find seller using product.getUser.getId()
        User seller = userRepository.findById(product.getUser().getId()).get();

        Long currentAmount = bid.getPriceStart();
        Long bidIncrement = bid.getStepBid();

        if (amount <= currentAmount )
            throw new IllegalArgumentException("Your bid must have higher amount than current amount");
        if (buyer == seller)
            throw new RuntimeException("Seller can not place a bid on this product");
        if(currentAmount + stepBid > amount)
            throw new RuntimeException("The bid increment for this product is " + bidIncrement + " VND. Your bid amount should be higher");

        BidHistory bidHistory = new BidHistory();
        bidHistory.setPrice(amount);
        bidHistory.setCreatedAt(LocalDateTime.now());
        bidHistory.setUser(buyer);
        bidHistory.setBid(bid);
//        bid.setPriceStart(amount);

        bidHistoryRepository.save(bidHistory);

        return new DataResponse<>(bidHistory);
    }
}

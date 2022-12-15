package com.hung.sneakery.data.remotes.services.impl;

import com.hung.sneakery.data.models.dto.request.BidCreateRequest;
import com.hung.sneakery.data.models.dto.request.BidPlaceRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.entities.*;
import com.hung.sneakery.data.remotes.repositories.*;
import com.hung.sneakery.data.remotes.services.BidService;
import com.hung.sneakery.data.remotes.services.CountdownService;
import com.hung.sneakery.data.remotes.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductDescriptionRepository productDescriptionRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    BidHistoryRepository bidHistoryRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    CountdownService countdownService;

    @Override
    public BaseResponse placeBid(BidPlaceRequest bidPlaceRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByUsername(userName);

        Long bidProductId = bidPlaceRequest.getProductId();

        if (!productRepository.findById(bidProductId).isPresent())
            throw new RuntimeException("Product not found");
        Product product = productRepository.findById(bidProductId).get();

        if(!bidRepository.findById(bidProductId).isPresent())
            throw new RuntimeException("Bid not found");
        Bid bid = bidRepository.findById(bidProductId).get();

        //Find seller
        if(!userRepository.findById(product.getUser().getId()).isPresent())
            throw new RuntimeException("Seller not found for this product");
        User seller = userRepository.findById(product.getUser().getId()).get();

        Long amount = bidPlaceRequest.getAmount();
        Long stepBid = bid.getStepBid();

        Optional<BidHistory> currentBidHistory = bidHistoryRepository.findFirstByBidIdOrderByPriceDesc(bidProductId);
        Long currentAmount;
        if(!currentBidHistory.isPresent())
             currentAmount = bid.getPriceStart();
        else currentAmount = currentBidHistory.get().getPrice();
        Long bidIncrement = bid.getStepBid();

        if (amount <= currentAmount )
            throw new IllegalArgumentException("Your bid must have higher amount than current amount");
        if (buyer == seller)
            throw new RuntimeException("Seller can not place a bid on this product");
        if(currentAmount + stepBid > amount)
            throw new RuntimeException("The bid increment for this product is " + bidIncrement + " $. Your bid amount should be higher");
        if(!compareWalletBalanceToCurrentBidPrice(currentAmount))
            throw new RuntimeException("Your wallet's balance is lower than the current price");
        if(!compareWalletBalanceToCurrentBidPrice(amount))
            throw new RuntimeException("Your wallet's balance is lower than the amount you wanna place a bid");

        BidHistory bidHistory = new BidHistory();
        bidHistory.setPrice(amount);
        bidHistory.setUser(buyer);
        bidHistory.setCreatedAt(LocalDateTime.now());
        bidHistory.setBid(bid);
        bidHistoryRepository.save(bidHistory);

        return new BaseResponse(true, "Place bid successfully");
    }

    @Override
    public BaseResponse createBid(BidCreateRequest bidCreateRequest, MultipartFile thumbnail, List<MultipartFile> images) throws IOException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(userName);

        Bid bid = mapToBid(bidCreateRequest, seller, thumbnail, images);

        //Call countdownService
        countdownService.biddingCountdown(bid);

        return new BaseResponse(true, "Created bidding product successfully");
    }

    private Boolean compareWalletBalanceToCurrentBidPrice(Long currentPrice){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User bidder = userRepository.findByUsername(userName);

        Wallet wallet = walletRepository.findByUser_Id(bidder.getId());
        Long walletBalance = wallet.getBalance();
        return walletBalance >= currentPrice;
    }

    private Bid mapToBid(BidCreateRequest bidCreateRequest, User seller, MultipartFile thumbnail, List<MultipartFile> images) throws IOException {
        //Map Category
        Category category = categoryRepository.findByCategoryName(bidCreateRequest.getCategory());

        //Map Product
        Product product = new Product();
        product.setName(bidCreateRequest.getName());
        product.setCondition(bidCreateRequest.getCondition());
        product.setUser(seller);
        product.setCategory(category);

        //Map ProductDescription
        ProductDescription productDescription = new ProductDescription();
        productDescription.setBrand(bidCreateRequest.getBrand());
        productDescription.setColor(bidCreateRequest.getColor());
        productDescription.setSize(bidCreateRequest.getSize());
        productDescription.setProduct(product);
        productDescriptionRepository.save(productDescription);

        //Save Product
        product.setProductDescription(productDescription);
        productRepository.save(product);

        //Save ProductImage
        List<ProductImage> productImages = new ArrayList<>();
        ProductImage image = productImageService.upload(thumbnail.getBytes(), product, true);
        productImages.add(image);
        for (MultipartFile file : images){
            ProductImage productImage = productImageService.upload(file.getBytes(), product, false);
            productImages.add(productImage);
        }
        productImageRepository.saveAll(productImages);

        //Save Bid
        Bid bid = new Bid();
        bid.setPriceStart(bidCreateRequest.getPriceStart());
        bid.setBidStartingDate(LocalDate.now());
        bid.setStepBid(bidCreateRequest.getStepBid());
        bid.setBidClosingDateTime(bidCreateRequest.getBidClosingDateTime());
        bid.setProduct(product);
        bidRepository.save(bid);

        return bid;
    }
}

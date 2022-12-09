package com.hung.sneakery.services.impl;

import com.hung.sneakery.model.*;
import com.hung.sneakery.payload.request.BidCreateRequest;
import com.hung.sneakery.payload.request.BidPlaceRequest;
import com.hung.sneakery.payload.response.BaseResponse;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.services.BidService;
import com.hung.sneakery.services.CountdownService;
import com.hung.sneakery.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    CountdownService countdownService;
    @Override
    public BaseResponse placeBid(BidPlaceRequest bidPlaceRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByUsername(userName);

        if (!productRepository.findById(bidPlaceRequest.getProductId()).isPresent())
            throw new RuntimeException("Product not found");
        Product product = productRepository.findById(bidPlaceRequest.getProductId()).get();

        if(!bidRepository.findById(bidPlaceRequest.getProductId()).isPresent())
            throw new RuntimeException("Bid not found");
        Bid bid = bidRepository.findById(bidPlaceRequest.getProductId()).get();

        Long amount = bidPlaceRequest.getAmount();
        Long stepBid = bid.getStepBid();

        //Find seller using product.getUser.getId()
        if(!userRepository.findById(product.getUser().getId()).isPresent())
            throw new RuntimeException("Seller not found for this product");
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

        return new BaseResponse(true, "Place bid successfully");
    }

    @Override
    public BaseResponse createBid(BidCreateRequest bidCreateRequest, MultipartFile thumbnail, List<MultipartFile> images) throws IOException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(userName);

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

        //Call countdownService
        countdownService.biddingCountdown(bid);

        return new BaseResponse(true, "Created bidding product successfully");
    }
}

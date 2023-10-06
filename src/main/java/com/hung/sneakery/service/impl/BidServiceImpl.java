package com.hung.sneakery.service.impl;

import com.hung.sneakery.data.models.dto.BidDTO;
import com.hung.sneakery.data.models.dto.ProductDTO;
import com.hung.sneakery.data.models.dto.request.BidCreateRequest;
import com.hung.sneakery.data.models.dto.request.BidPlaceRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;
import com.hung.sneakery.data.models.entities.*;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.service.BidService;
import com.hung.sneakery.service.CountdownService;
import com.hung.sneakery.service.ProductImageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ProductDescriptionRepository productDescriptionRepository;

    @Resource
    private BidRepository bidRepository;

    @Resource
    private BidHistoryRepository bidHistoryRepository;

    @Resource
    private ProductImageRepository productImageRepository;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private WalletRepository walletRepository;

    @Resource
    private CountdownService countdownService;

    @Override
    public BaseResponse placeBid(BidPlaceRequest bidPlaceRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByUsername(userName);

        Long bidProductId = bidPlaceRequest.getProductId();

        if (!productRepository.findById(bidProductId).isPresent()) {
            throw new RuntimeException("Product not found");
        }
        Product product = productRepository.findById(bidProductId).get();

        if (!bidRepository.findById(bidProductId).isPresent()) {
            throw new RuntimeException("Bid not found");
        }
        Bid bid = bidRepository.findById(bidProductId).get();

        //Find seller
        if (!userRepository.findById(product.getUser().getId()).isPresent()) {
            throw new RuntimeException("Seller not found for this product");
        }
        User seller = userRepository.findById(product.getUser().getId()).get();

        Long amount = bidPlaceRequest.getAmount();
        Long stepBid = bid.getStepBid();

        Optional<BidHistory> currentBidHistory = bidHistoryRepository.findFirstByBidIdOrderByPriceDesc(bidProductId);
        Long currentAmount;
        if (!currentBidHistory.isPresent()) {
            currentAmount = bid.getPriceStart();
        } else {
            currentAmount = currentBidHistory.get().getPrice();
        }
        Long bidIncrement = bid.getStepBid();

        if (amount <= currentAmount) {
            throw new IllegalArgumentException("Your bid must have higher amount than current amount");
        }
        if (buyer == seller) {
            throw new RuntimeException("Seller can not place a bid on this product");
        }
        if (currentAmount + stepBid > amount) {
            throw new RuntimeException("The bid increment for this product is " + bidIncrement + " $. Your bid amount should be higher");
        }
        if (!compareWalletBalanceToCurrentBidPrice(currentAmount)) {
            throw new RuntimeException("Your wallet's balance is lower than the current price");
        }
        if (!compareWalletBalanceToCurrentBidPrice(amount)) {
            throw new RuntimeException("Your wallet's balance is lower than the amount you wanna place a bid");
        }
        BidHistory bidHistory = BidHistory.builder()
                .price(amount)
                .user(buyer)
                .createdAt(LocalDateTime.now())
                .bid(bid)
                .build();
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

    @Override
    public DataResponse<List<BidDTO>> getAllUploadedProduct() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(userName);
        List<Product> uploadedProducts = productRepository.findByUser(seller);
        List<BidDTO> bidDTOList = new ArrayList<>();
        for (Product product : uploadedProducts) {
            Bid bid = bidRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Bid not found"));
            BidDTO bidDTO = BidDTO.builder()
                    .bidId(bid.getId())
                    .priceWin(bid.getPriceWin())
                    .stepBid(bid.getStepBid())
                    .priceStart(bid.getPriceStart())
                    .bidStartingDate(bid.getBidStartingDate())
                    .product(new ProductDTO(product))
                    .build();
            bidDTOList.add(bidDTO);
        }
        return new DataResponse<>(bidDTOList);
    }

    private Boolean compareWalletBalanceToCurrentBidPrice(Long currentPrice) {
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
        Product product = Product.builder()
                .name(bidCreateRequest.getName())
                .condition(bidCreateRequest.getCondition())
                .user(seller)
                .category(category)
                .build();
        //Map ProductDescription
        ProductDescription productDescription = ProductDescription.builder()
                .brand(bidCreateRequest.getBrand())
                .color(bidCreateRequest.getColor())
                .size(bidCreateRequest.getSize())
                .product(product)
                .build();
        productDescriptionRepository.save(productDescription);

        //Save Product
        product.setProductDescription(productDescription);
        productRepository.save(product);

        //Save ProductImage
        List<ProductImage> productImages = new ArrayList<>();
        ProductImage image = productImageService.upload(thumbnail.getBytes(), product, true);
        productImages.add(image);
        for (MultipartFile file : images) {
            ProductImage productImage = productImageService.upload(file.getBytes(), product, false);
            productImages.add(productImage);
        }
        productImageRepository.saveAll(productImages);

        //Save Bid
        Bid bid = Bid.builder()
                .priceStart(bidCreateRequest.getPriceStart())
                .bidStartingDate(LocalDate.now())
                .stepBid(bidCreateRequest.getStepBid())
                .bidClosingDateTime(bidCreateRequest.getBidClosingDateTime())
                .product(product)
                .build();
        bidRepository.save(bid);
        return bid;
    }
}

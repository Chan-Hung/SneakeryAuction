package com.hung.sneakery.service.impl;

import com.hung.sneakery.converter.ProductConverter;
import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.request.BidCreateRequest;
import com.hung.sneakery.dto.request.BidPlaceRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.*;
import com.hung.sneakery.enums.EBidStatus;
import com.hung.sneakery.exception.BidPlacingException;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.service.BidService;
import com.hung.sneakery.service.CountdownService;
import com.hung.sneakery.service.ProductImageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BidServiceImpl implements BidService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CategoryRepository categoryRepository;

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

    @Resource
    private ProductConverter productConverter;

    @Override
    @Transactional
    public BaseResponse placeBid(final BidPlaceRequest request) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByUsername(userName);

        Long bidProductId = request.getProductId();
        Product product = productRepository.findById(bidProductId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        Bid bid = product.getBid();
        if (buyer.equals(product.getUser())) {
            throw new BidPlacingException("Người bán không được phép tham gia đấu giá sản phẩm này");
        }
        Long amount = request.getAmount();
        Long stepBid = bid.getStepBid();
        BidHistory currentBidHistory = bidHistoryRepository
                .findFirstByBidIdAndStatusOrderByPriceDesc(bidProductId, EBidStatus.SUCCESS).orElse(null);
        Long currentAmount = Objects.isNull(currentBidHistory) ?
                bid.getPriceStart() : currentBidHistory.getPrice();
        Long bidIncrement = bid.getStepBid();
        checkBidIsValid(currentAmount, stepBid, amount, bidIncrement);
        BidHistory bidHistory = BidHistory.builder()
                .price(amount)
                .status(EBidStatus.SUCCESS)
                .user(buyer)
                .bid(bid)
                .build();
        bidHistoryRepository.save(bidHistory);
        return new BaseResponse("Place bid successfully");
    }

    private void checkBidIsValid(final Long currentAmount, final Long stepBid, final Long amount, final Long bidIncrement) {
        if (amount <= currentAmount) {
            throw new BidPlacingException("Lượt ra giá của bạn phải cao hơn số tiền hiện tại");
        }
        if (currentAmount + stepBid > amount) {
            throw new BidPlacingException("Bước giá cho sản phẩm này là " + bidIncrement + " $");
        }
        compareWalletBalance(currentAmount, amount);
    }

    private void compareWalletBalance(final Long currentPrice, final Long amount) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User bidder = userRepository.findByUsername(userName);
        Wallet wallet = walletRepository.findByUser_Id(bidder.getId());
        Long walletBalance = wallet.getBalance();
        if (walletBalance < currentPrice) {
            throw new BidPlacingException("Số dư ví không đủ cho lần ra giá này");
        }
        if (walletBalance < amount) {
            throw new BidPlacingException("Số dư ví không đủ cho lần ra giá này");
        }
    }

    @Override
    public BaseResponse createBid(final BidCreateRequest request, final MultipartFile thumbnail, final List<MultipartFile> images) throws IOException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(userName);

        Bid bid = mapToBid(request, seller, thumbnail, images);

        countdownService.biddingCountdown(bid);

        return new BaseResponse("Created bidding product successfully");
    }

    @Override
    public List<BidDTO> getAllUploadedProduct() {
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
                    .bidStartingDate(bid.getCreatedDate())
                    .product(productConverter.convertToProductDTO(product))
                    .build();
            bidDTOList.add(bidDTO);
        }
        return bidDTOList;
    }

    @Transactional
    Bid mapToBid(final BidCreateRequest request, final User seller, final MultipartFile thumbnail, final List<MultipartFile> images) throws IOException {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .user(seller)
                .category(category)
                .properties(request.getProperties())
                .build();

        productRepository.save(product);

        List<ProductImage> productImages = new ArrayList<>();
        ProductImage image = productImageService.upload(thumbnail.getBytes(), product, true);
        productImages.add(image);
        for (MultipartFile file : images) {
            ProductImage productImage = productImageService.upload(file.getBytes(), product, false);
            productImages.add(productImage);
        }
        productImageRepository.saveAll(productImages);

        Bid bid = Bid.builder()
                .priceStart(request.getPriceStart())
                .stepBid(request.getStepBid())
                .closingDateTime(request.getBidClosingDateTime())
                .product(product)
                .build();
        bidRepository.save(bid);
        return bid;
    }
}

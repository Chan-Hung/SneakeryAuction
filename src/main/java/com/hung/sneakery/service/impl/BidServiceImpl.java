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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BidServiceImpl implements BidService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidServiceImpl.class);

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
    private MediaRepository mediaRepository;

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

        if (Boolean.TRUE.equals(bid.getIsBidSnipping())) {
            handleBidSniping(bid);
        }
        return new BaseResponse("Place bid successfully");
    }

    private void handleBidSniping(final Bid bid) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime bidEndTime = bid.getClosingDateTime();
        LocalDateTime threeMinutesBeforeBidEnd = bidEndTime.minusMinutes(3);

        if (currentTime.isAfter(threeMinutesBeforeBidEnd) && (bidHistoryRepository.countByBid_IdAndCreatedDateAfter(bid.getId(), threeMinutesBeforeBidEnd) == 1)) {
            LOGGER.info("START EXTEND BID TIME");
            LOGGER.info(String.format("Current time: %s", currentTime)); //NOSONAR
            LOGGER.info(String.format("Bid end time: %s", bidEndTime)); //NOSONAR
            bid.setClosingDateTime(bid.getClosingDateTime().plusMinutes(3));
            countdownService.biddingCountdown(bid);
        }
    }

    private void checkBidIsValid(final Long currentAmount, final Long stepBid, final Long amount, final Long bidIncrement) {
        if (amount <= currentAmount) {
            throw new BidPlacingException("Lượt ra giá của bạn phải cao hơn số tiền hiện tại");
        }
        if (currentAmount + stepBid > amount) {
            throw new BidPlacingException("Bước giá cho sản phẩm này là " + bidIncrement + " $");
        }
    }

    @Override
    public BaseResponse createBid(final BidCreateRequest request) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(userName);

        Bid bid = mapToBid(request, seller);

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
    protected Bid mapToBid(final BidCreateRequest request, final User seller) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        List<Media> images = mediaRepository.findAllById(request.getImageIds());

        Product product = Product.builder()
                .name(request.getName())
                .user(seller)
                .category(category)
                .properties(request.getProperties())
                .images(images)
                .build();

        Bid bid = Bid.builder()
                .priceStart(request.getPriceStart())
                .stepBid(request.getStepBid())
                .closingDateTime(request.getBidClosingDateTime())
                .product(product)
                .isBidSnipping(request.getIsBidSniping())
                .build();

        return bidRepository.save(bid);
    }
}

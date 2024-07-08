package com.hung.sneakery.service.impl;

import com.hung.sneakery.config.AppProperties;
import com.hung.sneakery.converter.BidConverter;
import com.hung.sneakery.dto.BidDTO;
import com.hung.sneakery.dto.BidDetailDTO;
import com.hung.sneakery.dto.request.BidCreateRequest;
import com.hung.sneakery.dto.request.BidPlaceRequest;
import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.entity.*;
import com.hung.sneakery.enums.BidOutcome;
import com.hung.sneakery.enums.EBidStatus;
import com.hung.sneakery.exception.BidCreatingException;
import com.hung.sneakery.exception.BidPlacingException;
import com.hung.sneakery.exception.NotFoundException;
import com.hung.sneakery.repository.*;
import com.hung.sneakery.service.BidService;
import com.hung.sneakery.service.CountdownService;
import com.hung.sneakery.service.MailService;
import com.hung.sneakery.utils.SneakeryConstant;
import com.hung.sneakery.utils.SneakeryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class BidServiceImpl implements BidService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidServiceImpl.class);
    private static final String EMAIL_SUBJECT = "[Lời nhắc] Đấu giá của bạn đang diễn ra";
    private static final String EMAIL_TEMPLATE_PATH = "email-templates/reminder.html";

    @Resource
    private AppProperties appProperties;

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
    private MailService mailService;

    @Resource
    private SneakeryUtil sneakeryUtil;

    @Resource
    private BidConverter bidConverter;

    @Override
    public BaseResponse placeBid(final BidPlaceRequest request) {
        User buyer = sneakeryUtil.getCurrentUser();
        String message = SneakeryConstant.PLACE_BID_SUCCESSFULLY;
        boolean success = true;

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(SneakeryConstant.PRODUCT_NOT_FOUND));
        Bid bid = product.getBid();
        BidHistory currentHighestBid = bid.getBidHistories()
                .stream()
                .max(Comparator.comparing(BidHistory::getActualPrice))
                .orElse(null);

        checkBidderIsNotSeller(bid, buyer);
        checkBidIsValid(bid, currentHighestBid, request);
        Long currentPrice = handleAutomaticBidding(bid, currentHighestBid, request, buyer);
        createBidHistory(bid, buyer, request.getAmount(), currentPrice);

        if (Objects.equals(currentPrice, request.getAmount())) {
            success = false;
            message = SneakeryConstant.PLACE_BID_UNSUCCESSFULLY;
        }
        if (Boolean.TRUE.equals(bid.getIsBidSnipping())) {
            handleBidSniping(bid);
        }
        if (shouldRemindBidder(currentHighestBid, buyer)) {
            sendRemindBidderEmailAsync(currentHighestBid.getUser(), product, currentPrice);
        }
        return new BaseResponse(success, message);
    }

    private Long handleAutomaticBidding(final Bid bid, final BidHistory highestBidHistory, final BidPlaceRequest request, final User buyer) {
        Long currentPrice = bid.getPriceStart();

        if (highestBidHistory != null) {
            if (request.getAmount() < highestBidHistory.getMaxPrice() || Objects.equals(request.getAmount(), highestBidHistory.getMaxPrice())) {
                currentPrice = request.getAmount();
            } else if (request.getAmount() > highestBidHistory.getMaxPrice()) {
                currentPrice = highestBidHistory.getMaxPrice() + bid.getStepBid();
                bid.setHolder(buyer);
            }
        } else {
            bid.setHolder(buyer);
        }
        return currentPrice;
    }

    private void handleBidSniping(final Bid bid) {
        LOGGER.info("Extended minutes: {}", appProperties.getExtendedMinute());
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime bidEndTime = bid.getClosingDateTime();
        LocalDateTime threeMinutesBeforeBidEnd = bidEndTime.minusMinutes(appProperties.getExtendedMinute());

        if (currentTime.isAfter(threeMinutesBeforeBidEnd) && (bidHistoryRepository.countByBid_IdAndCreatedDateAfter(bid.getId(), threeMinutesBeforeBidEnd) == 1)) {
            LOGGER.info("START EXTEND BID TIME");
            LOGGER.info(String.format("New bid end time: %s", bidEndTime)); //NOSONAR
            bid.setClosingDateTime(bid.getClosingDateTime().plusMinutes(appProperties.getExtendedMinute()));
            bidRepository.save(bid);
            countdownService.biddingCountdown(bid);
        }
    }

    private boolean shouldRemindBidder(final BidHistory currentHighestBid, final User buyer) {
        return currentHighestBid != null && !currentHighestBid.getUser().equals(buyer);
    }

    private void sendRemindBidderEmailAsync(final User user, final Product product, final Long currentPrice) {
        CompletableFuture.runAsync(() -> {
            try {
                mailService.sendEmail(EMAIL_SUBJECT, EMAIL_TEMPLATE_PATH, user, product, currentPrice);
                LOGGER.info("Sent remind email to user: {}", user.getUsername());
            } catch (MessagingException | IOException | NullPointerException e) {
                LOGGER.info("Failed to send remind email due to: {}", e.getMessage());
            }
        });
    }

    private void checkBidderIsNotSeller(final Bid bid, final User buyer) {
        if (buyer.equals(bid.getProduct().getUser())) {
            throw new BidPlacingException("Người bán không được phép tham gia đấu giá sản phẩm này");
        }
    }

    final void checkBidIsValid(final Bid bid, final BidHistory currentHighestBid, final BidPlaceRequest request) {
        Long stepBid = bid.getStepBid();
        Long amount = request.getAmount();
        Long currentPrice = currentHighestBid != null ? currentHighestBid.getActualPrice() : bid.getPriceStart();
        if (amount <= currentPrice) {
            throw new BidPlacingException("Lượt ra giá của bạn phải cao hơn số tiền hiện tại");
        }
        if (currentPrice + stepBid > amount) {
            throw new BidPlacingException("Bước giá cho sản phẩm này là " + stepBid + " $");
        }
    }

    private void createBidHistory(final Bid bid, final User buyer, final Long amount, final Long currentPrice) {
        int numberOfBids = Objects.nonNull(bid.getNumberOfBids()) ? bid.getNumberOfBids() : 0;
        bid.setNumberOfBids(numberOfBids + 1);

        BidHistory bidHistory = BidHistory.builder()
                .maxPrice(amount)
                .actualPrice(currentPrice)
                .status(EBidStatus.SUCCESS)
                .user(buyer)
                .bid(bid)
                .build();
        bidHistoryRepository.save(bidHistory);
    }

    @Override
    public BidDetailDTO createBid(final BidCreateRequest request) {
        if (request.getBidClosingDateTime().isBefore(LocalDateTime.now())) {
            throw new BidCreatingException("Cannot create bid with closing date time in the past.");
        }
        User seller = sneakeryUtil.getCurrentUser();
        Bid bid = mapToBid(request, seller);
        countdownService.biddingCountdown(bid);
        return bidConverter.convertToBidDetailDTO(bid);
    }

    @Override
    public List<BidDTO> getUploadedProduct() {
        User seller = sneakeryUtil.getCurrentUser();

        List<Product> uploadedProducts = productRepository.findByUser(seller);
        List<BidDTO> bidDTOList = new ArrayList<>();
        for (Product product : uploadedProducts) {
            Bid bid = bidRepository.findById(product.getId()).orElseThrow(() -> new NotFoundException("Bid not found"));
            BidDTO bidDTO = bidConverter.convertToBidDTO(bid);
            bidDTOList.add(bidDTO);
        }
        return bidDTOList;
    }

    @Override
    public Page<BidDTO> getWinningBids(final Pageable pageable) {
        User user = sneakeryUtil.getCurrentUser();

        Page<Bid> bidsPage = bidRepository.findByHolderAndPriceWinNotNull(user, pageable);
        List<BidDTO> bidDTOs = bidConverter.convertToBidDTOList(bidsPage.getContent());
        return new PageImpl<>(bidDTOs, pageable, bidsPage.getTotalElements());
    }

    @Override
    public Page<BidDTO> getBidRevenue(Pageable pageable) {
        Page<Bid> bidsPage = bidRepository.findByBidOutcome(BidOutcome.CLOSED, pageable);
        List<BidDTO> bidDTOs = bidConverter.convertToBidDTOList(bidsPage.getContent());
        return new PageImpl<>(bidDTOs, pageable, bidsPage.getTotalElements());
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
                .description(request.getDescription())
                .properties(request.getProperties())
                .images(images)
                .build();

        Bid bid = Bid.builder()
                .priceStart(request.getPriceStart())
                .bidOutcome(BidOutcome.OPEN)
                .stepBid(request.getStepBid())
                .closingDateTime(request.getBidClosingDateTime())
                .reservePrice(request.getReservePrice())
                .product(product)
                .isBidSnipping(request.getIsBidSniping())
                .build();

        return bidRepository.save(bid);
    }
}

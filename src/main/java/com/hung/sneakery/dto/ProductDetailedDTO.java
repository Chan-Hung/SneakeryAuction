package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.entity.BidHistory;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;
import com.hung.sneakery.enums.ECondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class ProductDetailedDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("condition")
    private ECondition condition;

    @JsonProperty("startPrice")
    private Long startPrice;

    @JsonProperty("currentPrice")
    private Long currentPrice;

    @JsonProperty("bidIncrement")
    private Long bidIncrement;

    @JsonProperty("imagePath")
    private List<String> imagePath;

    @JsonProperty("category")
    private String category;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("color")
    private String color;

    @JsonProperty("size")
    private Integer size;

    //Format date time with JsonFormat
    //https://www.baeldung.com/jackson-jsonformat
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:00 dd/MM/yyyy")
    @JsonProperty("bidClosingDate")
    private LocalDateTime bidClosingDate;

    public ProductDetailedDTO(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setCondition(product.getCondition());
        this.setStartPrice(product.getBid().getPriceStart());
        List<String> imageList = new ArrayList<>();
        for (ProductImage productImage : product.getProductImage()) {
            String imagePathSingle = productImage.getPath();
            imageList.add(imagePathSingle);
        }
        this.setImagePath(imageList);
        this.setCategory(product.getCategory().getCategoryName());
        this.setBrand(product.getProductDescription().getBrand());
        this.setColor(product.getProductDescription().getColor());
        this.setSize(product.getProductDescription().getSize());
        this.setBidIncrement(product.getBid().getStepBid());

        //BidId = ProductId: OneToOne Relationship
        List<Long> bidId = new ArrayList<>();

        //Get BidHistory of that product
        for (BidHistory latestBidHistory : product.getBid().getBidHistories())
            if (Objects.equals(latestBidHistory.getBid().getProduct().getId(), product.getId()))
                bidId.add(latestBidHistory.getId());
        if (bidId.isEmpty())
            this.setCurrentPrice(product.getBid().getPriceStart());
        else {
            Long max = Collections.max(bidId);
            for (BidHistory latestBidHistory : product.getBid().getBidHistories())
                if (Objects.equals(latestBidHistory.getId(), max))
                    this.setCurrentPrice(latestBidHistory.getPrice());
        }
        this.setBidClosingDate(product.getBid().getBidClosingDateTime());
    }
}

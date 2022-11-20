package com.hung.sneakery.dto.product;

import com.hung.sneakery.model.BidHistory;
import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductImage;
import com.hung.sneakery.model.datatype.ECondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailedDto {
    private Long id;

    private String name;

    private ECondition condition;

    private Long startPrice;

    private Long currentPrice;

    private Long bidIncrement;

    private List<String> imagePath;

    private String category;

    private String brand;

    private String color;

    private Integer size;


    //Format date time with JsonFormat
    //https://www.baeldung.com/jackson-jsonformat
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:00 dd/MM/yyyy")
    private LocalDateTime bidClosingDate;

    public ProductDetailedDto(Product product){
        this.setId(product.getId());
        this.setName(product.getName());
        this.setCondition(product.getCondition());
        this.setStartPrice(product.getBid().getPriceStart());
        List<String> imageList = new ArrayList<>();
        for (ProductImage productImage: product.getProductImage()) {
            String imagePathSingle = productImage.getPath();
            imageList.add(imagePathSingle);
        }
        this.setImagePath(imageList);
        this.setCategory(product.getCategory().getCategoryName());
        this.setBrand(product.getProductDescription().getBrand());
        this.setColor(product.getProductDescription().getColor());
        this.setSize(product.getProductDescription().getSize());
        this.setBidIncrement(product.getBid().getStepBid());

        List<Long> productId = new ArrayList<>();
        for (BidHistory lastestBidHistory: product.getBid().getBidHistories()) {
            if(Objects.equals(lastestBidHistory.getBid().getProduct().getId(), product.getId())){
                productId.add(lastestBidHistory.getId());
            }
        }
        Long max = Collections.max(productId);
        for (BidHistory lastestBidHistory: product.getBid().getBidHistories()) {
            if(Objects.equals(lastestBidHistory.getId(), max)){
                this.setCurrentPrice(lastestBidHistory.getPrice());
            }
        }
        this.setBidClosingDate(product.getBid().getBidClosingDateTime());
    }
}

package com.hung.sneakery.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class StatisticsResponse {

    @JsonProperty("transactionStatistics")
    private TransactionStatistics transactionStatistics;

    @JsonProperty("productStatistics")
    private ProductStatistics productStatistics;

    @Data
    @Builder
    public static class TransactionStatistics {

        @JsonProperty("preSaleFeeStatistics")
        private PreSaleFeeStatistics preSaleFeeStatistics;

        @JsonProperty("postSaleFeeStatistics")
        private PostSaleFeeStatistics postSaleFeeStatistics;

        @JsonProperty("revenueStatistics")
        private RevenueStatistics revenueStatistics;
    }

    @Data
    @NoArgsConstructor
    public static class PreSaleFeeStatistics {

        @JsonProperty("total")
        private Long total;
    }

    @Data
    @NoArgsConstructor
    public static class PostSaleFeeStatistics {

        @JsonProperty("total")
        private Long total;

        @JsonProperty("average")
        private Long average;

        @JsonProperty("max")
        private Long max;

        @JsonProperty("min")
        private Long min;
    }

    @Data
    @NoArgsConstructor
    public static class RevenueStatistics {

        @JsonProperty("total")
        private Long total;

        @JsonProperty("average")
        private Long average;

        @JsonProperty("max")
        private Long max;

        @JsonProperty("min")
        private Long min;
    }

    @Data
    @Builder
    public static class ProductStatistics {

        @JsonProperty("productsTotal")
        private Long productsTotal;

        @JsonProperty("productsTotalByCategory")
        private List<ProductsTotalByCategory> productsTotalByCategory;
    }

    @Data
    @NoArgsConstructor
    public static class ProductsTotalByCategory {

        @JsonProperty("category")
        private String category;

        @JsonProperty("total")
        private Integer total;
    }
}

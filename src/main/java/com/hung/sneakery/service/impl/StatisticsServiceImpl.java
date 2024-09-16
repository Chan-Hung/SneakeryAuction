package com.hung.sneakery.service.impl;

import com.hung.sneakery.dto.response.StatisticsResponse;
import com.hung.sneakery.entity.Category;
import com.hung.sneakery.enums.EPaymentType;
import com.hung.sneakery.repository.CategoryRepository;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.repository.TransactionRepository;
import com.hung.sneakery.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private ProductRepository productRepository;

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private TransactionRepository transactionRepository;

    @Override
    public StatisticsResponse getStatisticsResult() {
        //Product Statistics
        List<Category> categories = categoryRepository.findAll();
        List<StatisticsResponse.ProductsTotalByCategory> productsTotalByCategories = new ArrayList<>();
        categories.forEach(category -> {
            StatisticsResponse.ProductsTotalByCategory productsTotalByCategory = new StatisticsResponse.ProductsTotalByCategory();
            productsTotalByCategory.setCategory(category.getName());
            productsTotalByCategory.setTotal(productRepository.countProductByCategory(category));
            productsTotalByCategories.add(productsTotalByCategory);
        });


        //PreSaleFee Statistics
        StatisticsResponse.PreSaleFeeStatistics preSaleFeeStatistics = new StatisticsResponse.PreSaleFeeStatistics();
        preSaleFeeStatistics.setTotal(transactionRepository.totalByPaymentType(EPaymentType.PRE_SALE_FEE));

        //PostSaleFee Statistics
        StatisticsResponse.PostSaleFeeStatistics postSaleFeeStatistics = new StatisticsResponse.PostSaleFeeStatistics();
        postSaleFeeStatistics.setTotal(transactionRepository.totalByPaymentType(EPaymentType.AUCTION_FEE));
        postSaleFeeStatistics.setAverage(transactionRepository.averageByPaymentType(EPaymentType.AUCTION_FEE));
        postSaleFeeStatistics.setMax(transactionRepository.maxByPaymentType(EPaymentType.AUCTION_FEE));
        postSaleFeeStatistics.setMin(transactionRepository.minByPaymentType(EPaymentType.AUCTION_FEE));


        //Revenue Statistics
        StatisticsResponse.RevenueStatistics revenueStatistics = new StatisticsResponse.RevenueStatistics();
        revenueStatistics.setTotal(transactionRepository.totalByPaymentType(EPaymentType.PAID));
        revenueStatistics.setAverage(transactionRepository.averageByPaymentType(EPaymentType.PAID));
        revenueStatistics.setMax(transactionRepository.maxByPaymentType(EPaymentType.PAID));
        revenueStatistics.setMin(transactionRepository.minByPaymentType(EPaymentType.PAID));

        StatisticsResponse.TransactionStatistics transactionStatistics = StatisticsResponse.TransactionStatistics
                .builder()
                .preSaleFeeStatistics(preSaleFeeStatistics)
                .postSaleFeeStatistics(postSaleFeeStatistics)
                .revenueStatistics(revenueStatistics)
                .build();

        StatisticsResponse.ProductStatistics productStatistics = StatisticsResponse.ProductStatistics
                .builder()
                .productsTotal(productRepository.count())
                .productsTotalByCategory(productsTotalByCategories)
                .build();

        return StatisticsResponse.builder().transactionStatistics(transactionStatistics).productStatistics(productStatistics).build();
    }
}

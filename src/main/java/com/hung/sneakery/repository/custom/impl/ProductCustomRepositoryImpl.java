package com.hung.sneakery.repository.custom.impl;

import com.hung.sneakery.entity.*;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
import com.hung.sneakery.repository.custom.ProductCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Product> productSearch(Pageable pageable, String keyword, String category, ECondition condition, List<String> brands,
                                       List<String> colors, List<Integer> sizes, Long priceStart,
                                       Long priceEnd, ESorting sorting) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = cb.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (Objects.nonNull(keyword)) {
            predicateList.add(cb.like(cb.lower(root.get(Product_.NAME)), "%" + keyword.toLowerCase() + "%"));
        }

        if (Objects.nonNull(category)) {
            Join<Product, Category> productCategoryJoin = root.join(Product_.CATEGORY);
            predicateList.add(cb.equal(cb.lower(productCategoryJoin.get(Category_.NAME)), category));
        }

        if (Objects.nonNull(condition)) {
//            predicateList.add(cb.equal(root.get(Product_.CONDITION), condition));
        }

        if (Objects.nonNull(brands)) {
//            CriteriaBuilder.In<String> inClause = cb.in(root.get(Product_.BRAND));
            for (String brand : brands) {
//                inClause.value(brand);
            }
//            predicateList.add(inClause);
        }

        if (Objects.nonNull(colors)) {
            //TODO: Return nothing
//            CriteriaBuilder.In<String> inClause = cb.in(root.get(Product_.COLOR));
            for (String color : colors) {
//                inClause.value(color);
            }
//            predicateList.add(inClause);
        }

        if (Objects.nonNull(sizes)) {
//            CriteriaBuilder.In<Integer> inClause = cb.in(root.get(Product_.SIZE));
            for (Integer size : sizes) {
//                inClause.value(size);
            }
//            predicateList.add(inClause);
        }

        //Properties of Bid
        Join<Product, Bid> productBidJoin = root.join(Product_.BID);
        if (priceStart != null) {
            predicateList.add(cb.greaterThanOrEqualTo(productBidJoin.get(Bid_.PRICE_START), priceStart));
        }

        if (priceEnd != null) {
            predicateList.add(cb.lessThanOrEqualTo(productBidJoin.get(Bid_.PRICE_START), priceEnd));
        }

        //Main query
        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        if (Objects.nonNull(sorting)) {
            switch (sorting) {
                case A_TO_Z:
                    criteriaQuery.orderBy(cb.asc(root.get(Product_.NAME)));
                    break;
                case LOW_TO_HIGH:
                    criteriaQuery.orderBy(cb.asc(productBidJoin.get(Bid_.PRICE_START)));
                    break;
                case HIGH_TO_LOW:
                    criteriaQuery.orderBy(cb.desc(productBidJoin.get(Bid_.PRICE_START)));
                    break;
                case NEWEST:
                    criteriaQuery.orderBy(cb.desc(productBidJoin.get(AbstractCommonEntity_.CREATED_DATE)));
                    break;
            }
        } else {
            criteriaQuery.orderBy(cb.desc(productBidJoin.get(AbstractCommonEntity_.CREATED_DATE)));
        }
        TypedQuery<Product> query = em.createQuery(criteriaQuery);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Product> products = query.getResultList();

        return new PageImpl<>(products, pageable, products.size());
    }
}

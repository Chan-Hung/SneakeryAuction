package com.hung.sneakery.repository.custom.impl;

import com.hung.sneakery.data.models.entities.*;
import com.hung.sneakery.entity.Bid;
import com.hung.sneakery.entity.Category;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductDescription;
import com.hung.sneakery.repository.custom.ProductCustomRepository;
import com.hung.sneakery.enums.ECondition;
import com.hung.sneakery.enums.ESorting;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Product> productSearch(String keyword, String category, ECondition condition, List<String> brands, List<String> colors, List<Integer> sizes, Long priceStart, Long priceEnd, ESorting sorting, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = cb.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (keyword != null)
            predicateList.add(cb.like(root.get(Product_.NAME), "%" + keyword + "%"));

        if (category != null) {
            Join<Product, Category> productCategoryJoin = root.join(Product_.CATEGORY);
            predicateList.add(cb.equal(productCategoryJoin.get(Category_.CATEGORY_NAME), category));
        }

        if (condition != null) {
            predicateList.add(cb.equal(root.get(Product_.CONDITION), condition));
        }

        //Properties of ProductDescription
        Join<Product, ProductDescription> productDescriptionJoin = root.join(Product_.PRODUCT_DESCRIPTION);

        if (brands != null) {
            List<Predicate> brandPredicate = new ArrayList<>();
            for (String brand : brands)
                brandPredicate.add(cb.equal(productDescriptionJoin.get(ProductDescription_.BRAND), brand));
            predicateList.add(addOrPredicate(brandPredicate));
        }


        if (colors != null) {
            List<Predicate> colorPredicate = new ArrayList<>();
            for (String color : colors)
                colorPredicate.add(cb.equal(productDescriptionJoin.get(ProductDescription_.COLOR), color));
            predicateList.add(addOrPredicate(colorPredicate));
        }

        if (sizes != null) {
            List<Predicate> sizePredicate = new ArrayList<>();
            for (Integer size : sizes)
                sizePredicate.add(cb.equal(productDescriptionJoin.get(ProductDescription_.SIZE), size));
            predicateList.add(addOrPredicate(sizePredicate));
        }

        //Properties of Bid
        Join<Product, Bid> productBidJoin = root.join(Product_.BID);
        if (priceStart != null)
            predicateList.add(cb.greaterThanOrEqualTo(productBidJoin.get(Bid_.PRICE_START), priceStart));

        if (priceEnd != null)
            predicateList.add(cb.lessThanOrEqualTo(productBidJoin.get(Bid_.PRICE_START), priceEnd));


        //Main query
        if (sorting != null)
            switch (sorting) {
                case A_TO_Z:
                    criteriaQuery
                            .where(predicateList.toArray(new Predicate[0]))
                            .orderBy(cb.asc(root.get(Product_.NAME)));
                    break;
                case LOW_TO_HIGH:
                    criteriaQuery
                            .where(predicateList.toArray(new Predicate[0]))
                            .orderBy(cb.asc(productBidJoin.get(Bid_.PRICE_START)));
                    break;
                case HIGH_TO_LOW:
                    criteriaQuery
                            .where(predicateList.toArray(new Predicate[0]))
                            .orderBy(cb.desc(productBidJoin.get(Bid_.PRICE_START)));
                    break;
                case NEWEST:
                    criteriaQuery
                            .where(predicateList.toArray(new Predicate[0]))
                            .orderBy(cb.desc(productBidJoin.get(Bid_.BID_STARTING_DATE)));
                    break;
            }
        else
            //Default sorting
            criteriaQuery
                .where(predicateList.toArray(new Predicate[0]))
                .orderBy(cb.asc(root.get(Product_.NAME)));
        //Count products after filtered
//        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//        Root<Product> productCount = countQuery.from(Product.class);
//        countQuery.select(cb.count(productCount)).where(predicateList.toArray(new Predicate[0]));
//        Long count = em.createQuery(countQuery).getSingleResult();
//        System.out.println(count);

        TypedQuery<Product> query = em.createQuery(criteriaQuery);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    //Add many OR predicates to 1 common predicate
    private Predicate addOrPredicate(List<Predicate> predicateList) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        Predicate predicate = null;
        int size = predicateList.size();
        switch (size) {
            case 1:
                predicate = cb.or(predicateList.get(0));
                break;
            case 2:
                predicate = cb.or(predicateList.get(0), predicateList.get(1));
                break;
            case 3:
                predicate = cb.or(predicateList.get(0), predicateList.get(1), predicateList.get(2));
                break;
            case 4:
                predicate = cb.or(predicateList.get(0), predicateList.get(1), predicateList.get(2), predicateList.get(3));
                break;
        }
        return predicate;
    }
}

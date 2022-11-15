package com.hung.sneakery.repository.custom.impl;

import com.hung.sneakery.model.*;
import com.hung.sneakery.model.datatype.ECondition;
import com.hung.sneakery.repository.custom.ProductCustomRepository;
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
    public List<Product> productSearch(String keyword, String category, ECondition condition, String brand, String color, String size, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = cb.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        List<Predicate> predicateList = new ArrayList<>();


        if(keyword != null)
            predicateList.add(cb.like(root.get(Product_.NAME),"%"+keyword+"%"));

        if(category != null){
            Join<Product, Category> productCategoryJoin = root.join(Product_.CATEGORY);
            predicateList.add(cb.equal(productCategoryJoin.get(Category_.CATEGORY_NAME),category));
        }

        if(condition!=null){
            predicateList.add(cb.equal(root.get(Product_.CONDITION), condition));
        }

        //Properties of ProductDescription
        Join<Product, ProductDescription> productDescriptionJoin = root.join(Product_.PRODUCT_DESCRIPTION);

        if (brand !=null){
            predicateList.add(cb.equal(productDescriptionJoin.get(ProductDescription_.BRAND),brand));
        }
        if(color != null){
            predicateList.add(cb.equal(productDescriptionJoin.get(ProductDescription_.COLOR),color));
        }
        if(size != null){
            predicateList.add(cb.equal(productDescriptionJoin.get(ProductDescription_.SIZE), size));
        }

        //Main query
        criteriaQuery.where(predicateList.toArray(new Predicate[0]));

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
}

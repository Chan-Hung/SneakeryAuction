package com.hung.sneakery.repository.custom.impl;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.repository.custom.ProductCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Product> productSearch(String category, String keyword) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        List<Predicate> predicateList = new ArrayList<>();

        if(category != null)
            predicateList.add(cb.equal(root.get("categoryName"),category));

        if(keyword != null)
            predicateList.add(cb.like(root.get("keyword"),"%"+keyword+"%"));
//
//        if(condition != null)
//            predicateList.add(cb.equal(root.get("condition"), condition ));
//
//        if (brand != null)
//            predicateList.add(cb.equal(root.get("productDescription"),brand));
//
//        if(price != null)
//            predicateList.add(cb.greaterThanOrEqualTo(root.get("productDescription"), price));
//        if(size != null)
//            predicateList.add(cb.equal(root.get("productDescription"), size));
//
//        Predicate predicate = cb.and(predicateList.toArray(new Predicate[0]));
//
//        cq.select(root).where(predicate);

        //Set page query at ProductController
//        TypedQuery<Product> query = em.createQuery(cq);
//
//        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
//        query.setMaxResults(pageable.getPageSize());

        Predicate predicate = cb.and(predicateList.toArray(new Predicate[0]));

        cq.select(root).where(predicate);
        TypedQuery<Product> query = em.createQuery(cq);


        return query.getResultList();

    }
}

package com.zimolo.inventory.repository;

import com.zimolo.inventory.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Product entity.
 */
public interface ProductRepository extends MongoRepository<Product,String> {
    Page<Product> findByCategory(String category, Pageable pageable);
    Product findBySku(int sku);
 }

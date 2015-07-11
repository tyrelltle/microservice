package com.zimolo.inventory.repository;

import com.zimolo.inventory.domain.CheckedOutProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Product entity.
 */
public interface CheckedOutProductRepository extends MongoRepository<CheckedOutProduct,String> {
}

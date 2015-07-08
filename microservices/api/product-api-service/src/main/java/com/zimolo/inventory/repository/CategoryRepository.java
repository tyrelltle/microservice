package com.zimolo.inventory.repository;


import com.zimolo.inventory.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Category entity.
 */
public interface CategoryRepository extends MongoRepository<Category,String> {

}

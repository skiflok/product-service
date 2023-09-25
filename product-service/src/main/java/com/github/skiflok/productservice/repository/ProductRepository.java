package com.github.skiflok.productservice.repository;

import com.github.skiflok.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}

package com.github.skiflok.productservice.service;


import com.github.skiflok.productservice.dto.ProductRequest;
import com.github.skiflok.productservice.dto.ProductResponse;
import com.github.skiflok.productservice.model.Product;
import com.github.skiflok.productservice.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public void createProduct(ProductRequest productRequest) {
    Product product = Product.builder()
        .name(productRequest.name())
        .description(productRequest.description())
        .price(productRequest.price())
        .build();

    productRepository.save(product);
    log.info("Product {} is saved", product.getId());

  }

  public List<ProductResponse> getAllProduct() {
    return productRepository.findAll().stream()
        .map(this::productToProductResponse)
        .toList();
  }
  public ProductResponse productToProductResponse(Product product){
    return          ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();
  }

}

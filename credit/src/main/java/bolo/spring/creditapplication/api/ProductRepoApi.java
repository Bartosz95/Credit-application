package bolo.spring.creditapplication.api;

import bolo.spring.creditapplication.domain.Product;

import java.util.List;

public interface ProductRepoApi {

    Product createProduct(Product product);

    List<Product> getProducts(List<Long> idList);
}

package pl.inteca.product.api;

import pl.inteca.product.domain.Product;

import java.util.List;

public interface ProductRepoApi {

    Product createProduct(Product product);

    List<Product> getProducts(List<Long> idList);
}

package pl.inteca.credit.api;

import pl.inteca.credit.domain.Product;

import java.util.List;

public interface ProductRepoApi {

    Product createProduct(Product product);

    List<Product> getProducts(List<Long> idList);
}

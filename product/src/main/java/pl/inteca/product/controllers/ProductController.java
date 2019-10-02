package pl.inteca.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.inteca.product.domain.Product;
import pl.inteca.product.repository.ProductRepository;
import java.util.List;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${server.path}")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping("${server.post}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Product createProduct(@RequestBody Product product) {
        return repository.createProduct(product);
    }

    @PostMapping("${server.get}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Product> getProducts(@RequestBody List<Long> idList) {
       return repository.getProducts(idList);
    }

}

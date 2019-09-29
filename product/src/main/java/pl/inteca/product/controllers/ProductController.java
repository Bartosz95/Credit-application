package pl.inteca.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inteca.product.domain.Product;
import pl.inteca.product.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping
    public @ResponseBody
    Product createProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping
    public @ResponseBody
    List<Product> getProducts() {
       return repository.findAll();
    }

}

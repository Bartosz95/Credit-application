package pl.inteca.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import pl.inteca.product.domain.Product;
import pl.inteca.product.repository.ProductRepository;
import java.util.List;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${server.path}")
public class ProductController {

    @Value("${spring.datasource.url}")
    private String url;

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public @ResponseBody
    String fcn(){
        return url;
    }


    @PostMapping("${server.post}")
    public @ResponseBody
    Product createProduct(@RequestBody Product product) {
        return repository.createProduct(product);
    }

    @PostMapping("${server.get}")
    public @ResponseBody
    List<Product> getProducts(@RequestBody List<Long> idList) {
       return repository.getProducts(idList);
    }

}

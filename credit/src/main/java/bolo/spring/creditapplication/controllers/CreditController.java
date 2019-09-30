package bolo.spring.creditapplication.controllers;

import bolo.spring.creditapplication.domain.Credit;
import bolo.spring.creditapplication.domain.CreditInfo;
import bolo.spring.creditapplication.domain.Customer;
import bolo.spring.creditapplication.domain.Product;
import bolo.spring.creditapplication.repository.CreditRepository;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${server.path}")
public class CreditController {

    @Value("${products.url}")
    String productUrl;
    @Value("${customers.url}")
    String customersUrl;

    @Autowired
    private CreditRepository repository;

    @Autowired
    RestTemplate restTemplate;


    @PostMapping
    public @ResponseBody
    long createCredit(@RequestBody CreditRequest request){

        // todo
        System.out.println(request);

//        Credit credit = Credit.builder()
//                .name(name)
//                .build();

        return 1L; //repository.save(credit).getId();
    }

    @GetMapping
    public @ResponseBody
    Iterable<Credit> getCredits(){
      return repository.findAll();
    }



    List<Customer> getCustomers(){
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(productUrl, Customer[].class);
        List<Customer> customers = new ArrayList<>();
        Collections.addAll(customers, Objects.requireNonNull(response.getBody()));
        return customers;
    }

    List<Product> getProducts(){
        ResponseEntity<Product[]> response = restTemplate.getForEntity(productUrl, Product[].class);
        List<Product> products = new ArrayList<>();
        Collections.addAll(products, Objects.requireNonNull(response.getBody()));
        return products;
    }




}

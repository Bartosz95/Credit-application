package bolo.spring.creditapplication.controllers;

import bolo.spring.creditapplication.domain.Credit;
import bolo.spring.creditapplication.domain.Customer;
import bolo.spring.creditapplication.domain.Product;
import bolo.spring.creditapplication.repository.CreditRepository;
import bolo.spring.creditapplication.repository.CreditRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
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

    @Autowired
    private CreditRepository repository;

    @PostMapping("${server.post}")
    public @ResponseBody
    long createCredit(@RequestBody CreditRequest request){

        Customer customer = repository.createCustomer(request.getCustomer());
        Product product = repository.createProduct(request.getProduct());

        Credit credit = request.getCredit();
        credit.setCustomerId(customer.getId());
        credit.setProductId(product.getId());
        return repository.save(credit).getId();
    }

    @GetMapping("${server.get}")
    public @ResponseBody
    List<CreditRequest> getCredits(){

        List<Customer> customers = repository.getCustomers();

        List<Product> products = repository.getProducts();

        List<Credit> credits = repository.findAll();

        List<CreditRequest> creditList = new ArrayList<>();

        for(Credit credit : credits){
            Customer customer = customers.stream()
                    .filter(customer1 -> credit.getCustomerId() == customer1.getId())
                    .findFirst()
                    .orElse(null);

            Product product = products.stream()
                    .filter(product1 -> credit.getProductId() == product1.getId())
                    .findFirst()
                    .orElse(null);

            creditList.add(CreditRequest.builder()
                    .credit(credit)
                    .customer(customer)
                    .product(product)
                    .build());
        }

        return creditList;
    }






}

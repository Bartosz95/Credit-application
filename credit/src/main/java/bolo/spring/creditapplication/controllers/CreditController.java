package bolo.spring.creditapplication.controllers;

import bolo.spring.creditapplication.domain.Credit;
import bolo.spring.creditapplication.domain.Customer;
import bolo.spring.creditapplication.domain.Product;
import bolo.spring.creditapplication.repository.CreditRepository;
import bolo.spring.creditapplication.repository.CreditRowMapper;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.DataOutput;
import java.io.IOException;
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

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("${server.post}")
    public @ResponseBody
    long createCredit(@RequestBody CreditMapper request){

        Customer customer = repository.createCustomer(request.getCustomer());
        Product product = repository.createProduct(request.getProduct());

        Credit credit = request.getCredit();
        credit.setCustomerId(customer.getId());
        credit.setProductId(product.getId());
        return repository.createCredit(credit).getId();
    }

    @GetMapping("${server.get}")
    @JsonView(Views.Public.class) // This fcn using mapper to delete variable without annotation @JsonView(Views.Public.class)
    public @ResponseBody
    List<CreditMapper> getCredits() throws IOException {

        // Get all credit from database
        List<Credit> credits = repository.getCredits();

        // Get all id from credits
        List<Long> idCustomerList = new ArrayList<>();
        List<Long> idProductList = new ArrayList<>();
        for(Credit credit: credits){
            idCustomerList.add(credit.getCustomerId());
            idProductList.add(credit.getProductId());
        }

        // Get all customers form database
        List<Customer> customers = repository.getCustomers(idCustomerList);

        // Get all product form database
        List<Product> products = repository.getProducts(idProductList);


        // Create request list
        List<CreditMapper> response = new ArrayList<>();
        //List<String[]> response = new ArrayList<>();
        String customerResponse;
        String productResponse;
        String creditResponse;

        for(Credit credit : credits){

            // Find customer by credit.customerId
            Customer customer = customers.stream()
                    .filter(customer1 -> credit.getCustomerId() == customer1.getId())
                    .findFirst()
                    .orElse(null);

            // Find product by credit.customerId
            Product product = products.stream()
                    .filter(product1 -> credit.getProductId() == product1.getId())
                    .findFirst()
                    .orElse(null);

            // Add (customer, product, credit) info to response
            response.add(CreditMapper.builder()
            .customer(customer)
            .product(product)
            .credit(credit)
            .build());
        }
        return response;
    }
}

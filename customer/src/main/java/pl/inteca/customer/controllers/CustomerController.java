package pl.inteca.customer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inteca.customer.domain.Customer;
import pl.inteca.customer.repository.CustomerRepository;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @PostMapping
    public @ResponseBody Customer createCustomer(@RequestBody Customer customer){
        return repository.save(customer);
    }

    @GetMapping
    public @ResponseBody
    List<Customer> getProducts() {
        return repository.findAll();
    }
}

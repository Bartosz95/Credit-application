package pl.inteca.customer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.inteca.customer.domain.Customer;
import pl.inteca.customer.repository.CustomerRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${server.path}")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    /*
     * Mapping service CreateCustomer
     * IN: JSON Customer object without id
     * OUT JSON Customer object with id
     */
    @PostMapping("${server.post}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Customer createCustomer(@RequestBody Customer customer){
        return repository.createCustomer(customer);
    }

    /*
     * Mapping service GetCustomer
     * IN:  JSON List id of needed object
     * OUT: JSON List of Customer object
     */
    @PostMapping("${server.get}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Customer> getProducts(@RequestBody List<Long> idList) {
        return repository.getCustomers(idList);
    }
}

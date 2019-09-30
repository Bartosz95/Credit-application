package bolo.spring.creditapplication.api;

import bolo.spring.creditapplication.domain.Customer;

import java.util.List;

public interface CustomerRepoApi {

    Customer createCustomer(Customer customer);

    List<Customer> getCustomers(List<Long> idList);
}

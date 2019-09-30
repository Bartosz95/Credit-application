package pl.inteca.customer.api;

import pl.inteca.customer.domain.Customer;

import java.util.List;

public interface CustomerRepoApi {

    Customer createCustomer(Customer customer);

    List<Customer> getCustomers(List<Long> idList);
}

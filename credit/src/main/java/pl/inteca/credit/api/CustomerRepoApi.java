package pl.inteca.credit.api;

import pl.inteca.credit.domain.Customer;

import java.util.List;

public interface CustomerRepoApi {

    Customer createCustomer(Customer customer);

    List<Customer> getCustomers(List<Long> idList);
}

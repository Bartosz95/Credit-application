package bolo.spring.creditapplication.controllers;

import bolo.spring.creditapplication.domain.Credit;
import bolo.spring.creditapplication.domain.Customer;
import bolo.spring.creditapplication.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequest {
    Customer customer;
    Product product;
    Credit credit;

}

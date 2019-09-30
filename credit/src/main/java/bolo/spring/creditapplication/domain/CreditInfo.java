package bolo.spring.creditapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditInfo {
    Customer customer;
    Product product;
    Credit credit;

}

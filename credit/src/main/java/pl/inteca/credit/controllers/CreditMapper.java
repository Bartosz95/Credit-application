package pl.inteca.credit.controllers;

import pl.inteca.credit.domain.Credit;
import pl.inteca.credit.domain.Customer;
import pl.inteca.credit.domain.Product;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*
    Mapper structuring all element to clearly response
 */
public class CreditMapper {
    @JsonView(Views.Public.class)
    Customer customer;

    @JsonView(Views.Public.class)
    Product product;

    @JsonView(Views.Public.class)
    Credit credit;
}

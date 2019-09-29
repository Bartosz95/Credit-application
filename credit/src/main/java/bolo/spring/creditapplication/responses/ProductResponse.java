package bolo.spring.creditapplication.responses;

import bolo.spring.creditapplication.domain.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    private String type;

    private List<Product> products;
}

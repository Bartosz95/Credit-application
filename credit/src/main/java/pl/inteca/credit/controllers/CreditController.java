package pl.inteca.credit.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.inteca.credit.domain.Credit;
import pl.inteca.credit.domain.Customer;
import pl.inteca.credit.domain.Product;
import pl.inteca.credit.repository.CreditRepository;

import java.util.ArrayList;
import java.util.List;


@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${server.path}")
public class CreditController {

    @Autowired
    private CreditRepository repository;

    @Autowired
    private ObjectMapper mapper;


    @PostMapping("${server.post}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    long createCredit(@RequestBody CreditMapper request){

        // create and save information about customer and product in database
        // function return Json object with ID
        Customer customer = repository.createCustomer(request.getCustomer());
        Product product = repository.createProduct(request.getProduct());

        // next to set information included id
        Credit credit = request.getCredit();
        credit.setCustomerId(customer.getId());
        credit.setProductId(product.getId());

        // create new user and return it id
        return repository.createCredit(credit).getId();
    }

    @GetMapping("${server.get}")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Public.class) // This fcn using mapper to delete variable without annotation @JsonView(Views.Public.class)
    public @ResponseBody
    List<CreditMapper> getCredits() {

        // Get all credit from database
        List<Credit> credits = repository.getCredits();

        // Get necessary id from credits
        List<Long> idCustomerList = new ArrayList<>();
        List<Long> idProductList = new ArrayList<>();
        for(Credit credit: credits){
            idCustomerList.add(credit.getCustomerId());
            idProductList.add(credit.getProductId());
        }

        // Get customers and product lists form database
        List<Customer> customers = repository.getCustomers(idCustomerList);
        List<Product> products = repository.getProducts(idProductList);

        // Create request list
        List<CreditMapper> response = new ArrayList<>();

        for(Credit credit : credits){

            // Connect customers and products with credits by id
            Customer customer = customers.stream()
                    .filter(customer1 -> credit.getCustomerId() == customer1.getId())
                    .findFirst()
                    .orElse(null);

            Product product = products.stream()
                    .filter(product1 -> credit.getProductId() == product1.getId())
                    .findFirst()
                    .orElse(null);

            // Add (customer, product, credit) to response list
            response.add(CreditMapper.builder()
            .customer(customer)
            .product(product)
            .credit(credit)
            .build());
        }
        return response;
    }
}

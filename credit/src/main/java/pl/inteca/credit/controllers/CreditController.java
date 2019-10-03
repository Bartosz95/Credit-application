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


    /*
     * Mapping POST service named CreateCredit It create and save information
     * about customer and product in database function return id
     * Long description about insert method in README.md
     * { "customer" : {
	 *      "firstName" : "Jan",
	 *      "lastName" : "Kowalski",
	 *      "personalId" : 1234567890 },
	 *   "product" : {
	 *      "name" : "motgage credit",
	 *      "value" : 2000 },
	 *    "credit" : {
	 *      "name": "mortgage" } }
     * IN: JSON Object looks like  object without id
     * OUT id
     */
    @PostMapping("${server.post}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    long createCredit(@RequestBody CreditMapper request){

        // Save customer and product in database
        // This function return object with id
        Customer customer = repository.createCustomer(request.getCustomer());
        Product product = repository.createProduct(request.getProduct());

        // Create credit and set customer and product id
        Credit credit = request.getCredit();
        credit.setCustomerId(customer.getId());
        credit.setProductId(product.getId());

        // create new credit and return it id
        return repository.createCredit(credit).getId();
    }

    /*
     * Mapping GET service named GetCredit
     * This function return list of all credit
     * IN: -
     * OUT: List of credit in JSON
     */
    @GetMapping("${server.get}")
    @ResponseStatus(HttpStatus.OK)
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

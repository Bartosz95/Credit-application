package pl.inteca.credit.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import pl.inteca.credit.api.CreditRepoApi;
import pl.inteca.credit.api.CustomerRepoApi;
import pl.inteca.credit.api.ProductRepoApi;
import pl.inteca.credit.domain.Credit;
import pl.inteca.credit.domain.Customer;
import pl.inteca.credit.domain.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*
    It communicate with 'customer' and 'product' by REST service.
    Implements all interface because it deliver all necessary
    information about Credit Customer and Product to controller.
 */
@Data
@NoArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class CreditRepository implements CreditRepoApi, CustomerRepoApi, ProductRepoApi {

    /*
     * values with annotation @Value are inside file /src/main/resources/application.properties
     */
    @Value("${products.url.get}")
    private String productUrlGet;
    @Value("${products.url.post}")
    private String productUrlPost;

    @Value("${customers.url.get}")
    private String customersUrlGet;
    @Value("${customers.url.post}")
    private String customersUrlPost;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    /*
     * Function create table "client" in database
     * table product have (id, name, customerId, productId)
     * IN: -
     * OUT -
     */
    @Autowired
    public void createTable() throws InterruptedException {
        String query = "CREATE TABLE IF NOT EXISTS credits (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "customerId BIGINT NOT NULL," +
                "productId BIGINT NOT NULL," +
                "PRIMARY KEY (id))";
        jdbcTemplate.execute(query);
    }

    /*
     * Function save product in credit-db.credit table
     * IN: Credit object without id, database connection
     * OUT: The same Credit object with id
     */
    @Override
    public Credit createCredit(Credit credit) {
        String query = String.format("INSERT INTO credits (name, customerId, productId) VALUES ('%s', '%d', '%d')",
                credit.getName(), credit.getCustomerId(), credit.getProductId());
        jdbcTemplate.update(query);
        query = "SELECT * FROM credits WHERE id=LAST_INSERT_ID()"; // get new credit id
        return jdbcTemplate.queryForObject(query, new CreditRowMapper());
    }

    /*
     * Function return all credit form table credit
     * Database send response like result list.
     * IN: -
     * OUT: List of credit object
     */
    @Override
    public List<Credit> getCredits() {
        String query = "SELECT * FROM credits";
        return jdbcTemplate.query(query, new CreditRowMapper());
    }

    /*
     * Function send Post Json message to http://customer-server:8092/CreateCustomer
     * with object which want to save in database. Database attribute id and return object
     * with all parameters
     * IN: Customer object without id, connection to database
     * OUT:Customer object with id
     */
    @Override
    public Customer createCustomer(Customer customer){
        HttpEntity<Customer> request = new HttpEntity<>(customer);
        return restTemplate.postForObject(customersUrlPost, request, Customer.class);
    }

    /*
     * Function send Post Json message to http://customer-server:8092/GetCustomers
     * with list of id customer. Service return response with customer objects with the same id
     * IN: Id list, connection to database
     * OUT: List of customer objects
     */
    @Override
    public List<Customer> getCustomers(List<Long> idList){
        HttpEntity<List<Long>> request = new HttpEntity<>(idList);
        ResponseEntity<Customer[]> response = restTemplate.postForEntity(customersUrlGet, request, Customer[].class);
        List<Customer> customers = new ArrayList<>();
        Collections.addAll(customers, Objects.requireNonNull(response.getBody()));
        return customers;
    }

    /*
     * Function send Post Json message to http://product-server:8091/CreateProduct
     * with object which want to save it in database. Database attribute id and return object
     * with all parameters
     * IN: Product object without id, connection to database
     * OUT:Product object with id
     */
    @Override
    public Product createProduct(Product product){
        HttpEntity<Product> request = new HttpEntity<>(product);
        return restTemplate.postForObject(productUrlPost, request, Product.class);
    }

    /*
     * Function send Post Json message to http://product-server:8091/GetProducts
     * with list of id product. Service return response with product objects with the same id
     * IN: Id list, connection to database
     * OUT: List of customer objects
     */
    @Override
    public List<Product> getProducts(List<Long> idList){
        HttpEntity<List<Long>> request = new HttpEntity<>(idList);
        ResponseEntity<Product[]> response = restTemplate.postForEntity(productUrlGet, request, Product[].class);
        List<Product> products = new ArrayList<>();
        Collections.addAll(products, Objects.requireNonNull(response.getBody()));
        return products;
    }
}

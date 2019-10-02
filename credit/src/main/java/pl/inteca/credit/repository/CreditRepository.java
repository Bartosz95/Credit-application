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

    // Function create table "product" in database but before wait 10 second for database
    @Autowired
    public void createTable() {
        // create query
        String query = "CREATE TABLE IF NOT EXISTS credits (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "customerId BIGINT NOT NULL," +
                "productId BIGINT NOT NULL," +
                "PRIMARY KEY (id))";

        // wait 10 second
        try {
            for(int i = 10; i>0; i--){
                System.out.println(String.format("wait for database: %d second", i));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // create a table and check connection
        try {
            jdbcTemplate.execute(query);
            System.out.println("Good connect to database");
        } catch (Exception e){
            System.out.println(String.format("Database server not response. Error: \n %s", e.getMessage()));
        }
    }

    // create credit in database and
    @Override
    public Credit createCredit(Credit credit) {
        String query = String.format("INSERT INTO credits (name, customerId, productId) VALUES ('%s', '%d', '%d')",
                credit.getName(), credit.getCustomerId(), credit.getProductId());
        jdbcTemplate.update(query);
        query = "SELECT * FROM credits WHERE id=LAST_INSERT_ID()"; // get new credit id
        return jdbcTemplate.queryForObject(query, new CreditRowMapper());
    }

    @Override
    public List<Credit> getCredits() {
        String query = "SELECT * FROM credits";
        return jdbcTemplate.query(query, new CreditRowMapper());
    }

    @Override
    public Customer createCustomer(Customer customer){
        HttpEntity<Customer> request = new HttpEntity<>(customer);
        return restTemplate.postForObject(customersUrlPost, request, Customer.class);
    }


    @Override
    public List<Customer> getCustomers(List<Long> idList){
        HttpEntity<List<Long>> request = new HttpEntity<>(idList);
        ResponseEntity<Customer[]> response = restTemplate.postForEntity(customersUrlGet, request, Customer[].class);
        List<Customer> customers = new ArrayList<>();
        Collections.addAll(customers, Objects.requireNonNull(response.getBody()));
        return customers;
    }

    @Override
    public Product createProduct(Product product){
        HttpEntity<Product> request = new HttpEntity<>(product);
        return restTemplate.postForObject(productUrlPost, request, Product.class);
    }

    @Override
    public List<Product> getProducts(List<Long> idList){
        HttpEntity<List<Long>> request = new HttpEntity<>(idList);
        ResponseEntity<Product[]> response = restTemplate.postForEntity(productUrlGet, request, Product[].class);
        List<Product> products = new ArrayList<>();
        Collections.addAll(products, Objects.requireNonNull(response.getBody()));
        return products;
    }
}

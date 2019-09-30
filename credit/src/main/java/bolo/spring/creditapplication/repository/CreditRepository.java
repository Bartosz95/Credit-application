package bolo.spring.creditapplication.repository;

import bolo.spring.creditapplication.api.Repository;
import bolo.spring.creditapplication.controllers.CreditRequest;
import bolo.spring.creditapplication.domain.Credit;
import bolo.spring.creditapplication.domain.Customer;
import bolo.spring.creditapplication.domain.Product;
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

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class CreditRepository implements Repository<Credit, Long> {

    @Value("${products.url}")
    private String productUrl;
    @Value("${customers.url}")
    private String customersUrl;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    // Function createTable() create table "product" in database
    @Autowired
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS credits (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "customerId BIGINT NOT NULL," +
                "productId BIGINT NOT NULL," +
                "PRIMARY KEY (id))";
        jdbcTemplate.execute(query);
    }

    @Override
    public Credit save(Credit credit) {
        String query = String.format("INSERT INTO credits (name, customerId, productId) VALUES ('%s', '%d', '%d')",
                credit.getName(), credit.getCustomerId(), credit.getProductId());
        jdbcTemplate.update(query);
        query = "SELECT * FROM credits WHERE id=LAST_INSERT_ID()"; // get new credit id
        return jdbcTemplate.queryForObject(query, new CreditRowMapper());
    }

    @Override
    public List<Credit> findAll() {
        String query = "SELECT * FROM credits";
        return jdbcTemplate.query(query, new CreditRowMapper());
    }

    public Customer createCustomer(Customer customer){
        HttpEntity<Customer> request = new HttpEntity<>(customer);
        return restTemplate.postForObject(customersUrl, request, Customer.class);
    }

    //assertThat(foo, notNullValue());
    //assertThat(foo.getName(), is("bar"));
    public Product createProduct(Product product){
        HttpEntity<Product> request = new HttpEntity<>(product);
        return restTemplate.postForObject(productUrl, request, Product.class);
    }

    public List<Customer> getCustomers(){
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(customersUrl, Customer[].class);
        List<Customer> customers = new ArrayList<>();
        Collections.addAll(customers, Objects.requireNonNull(response.getBody()));
        return customers;
    }

    public List<Product> getProducts(){
        ResponseEntity<Product[]> response = restTemplate.getForEntity(productUrl, Product[].class);
        List<Product> products = new ArrayList<>();
        Collections.addAll(products, Objects.requireNonNull(response.getBody()));
        return products;
    }
}

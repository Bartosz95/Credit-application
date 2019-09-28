package pl.inteca.product.repository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pl.inteca.product.api.Repository;
import pl.inteca.product.domain.Product;

import java.security.PrivateKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Data
@PropertySource("classpath:application.properties")
@Component
public class ProductRepository implements Repository<Product, Long> {

    @Value("${database.url}")
    private String url;
    @Value("${database.user}")
    private String user;
    @Value("${database.password}")
    private String password;
    @Value("${database.driver}")
    private String driver;

    private Connection connection;

    private Statement statement;

    String query;

    public ProductRepository() throws ClassNotFoundException, SQLException {
        query ="CREATE TABLE product (ID LONG NOT NULL, NAME VARCHAR(100), VALUE LONG PRIMARY KEY (ID)) IF NOT EXIST";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();
        System.out.println(statement.executeUpdate(query));
        // todo continue create database
        statement.close();
        connection.close();
    }




    @Override
    public Product save(Product item) throws SQLException, ClassNotFoundException {
        query = String.format("INSERT INTO product(name, value) VALUES (NULL,%s, %d)",item.getName(), item.getValue());
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(query);
        statement.close();
        connection.close();
        Product product = new Product();
        product.setId(i); // todo find and return new object
        return product;
    }

    @Override
    public List<Product> findAll() throws ClassNotFoundException, SQLException {
        query = "SELECT * FROM product";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        List<Product> products = new ArrayList<>();
        while (result.next()){
            Product product = Product.builder()
                    .id(result.getLong("id"))
                    .name(result.getString("name"))
                    .value(result.getLong("value"))
                    .build();
            products.add(product);
        }
        statement.close();
        connection.close();
        return products;
    }

    @Override
    public Product findAllById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Product item) {

    }

    @Override
    public void deleteAll() {

    }
}
package pl.inteca.product.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.inteca.product.api.Repository;
import pl.inteca.product.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class ProductRepository implements Repository<Product, Long> {

    // values with annotation @Value are inside file /src/main/resources/application.properties
    @Value("${database.url}")
    private String url;
    @Value("${database.user}")
    private String user;
    @Value("${database.password}")
    private String password;
    @Value("${database.driver}")
    private String driver;

    @Autowired // apply driver for mysql database
    public void applyDriver() throws ClassNotFoundException {
        Class.forName(driver);
    }

    // Function createTable() create table "product" in database
    @Autowired
    public void createTable() throws SQLException {
            String query = "CREATE TABLE IF NOT EXISTS products (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(255)," +
                    "value BIGINT," +
                    "PRIMARY KEY (id))";
            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
    }

    // Function save product in table product
    @Override
    public Product save(Product product) {
        try (Connection connection = DriverManager.getConnection(url,user,password);
             Statement statement = connection.createStatement()) {
            // insert product into table
            // id is autoincrement
            String query = String.format("INSERT INTO products (name, value) " +
                    "VALUES ('%s', '%d' )", product.getName(), product.getValue());
            statement.executeUpdate(query);
            query = "SELECT LAST_INSERT_ID() AS id FROM products"; // get new product id
            ResultSet result = statement.executeQuery(query);
            if(result.next())
                product.setId(result.getLong("id"));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return product;
    }

    // Function return all products form table products
    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM products";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                products.add(Product.builder() // create product by Builder and add to list
                        .id(result.getLong("id"))
                        .name(result.getString("name"))
                        .value(result.getLong("value"))
                        .build());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return products;
    }
}
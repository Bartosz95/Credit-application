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
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class ProductRepository implements Repository<Product, Long> {

    // values with annotation @Value are inside file  /src/main/resources/application.properties
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

    // Function createTable() create table "product" in database
    @Autowired
    public void createTable() throws ClassNotFoundException, SQLException {
            query = "CREATE TABLE IF NOT EXISTS products (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(255)," +
                    "value BIGINT," +
                    "PRIMARY KEY (id))";
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
    }

    // Function save product in table product
    @Override
    public Product save(Product product) throws ClassNotFoundException, SQLException {
        query = String.format("INSERT INTO products (name, value) VALUES ('%s', '%d' )", // id autoincrement that why we don't insert it
                product.getName(), product.getValue());
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        ResultSet result = statement.executeQuery("SELECT LAST_INSERT_ID() AS id FROM products");  // get current product id
        if(result.next()){
            long id = result.getLong("id");
            product.setId(id);
        }
        statement.close();
        connection.close();
        return product;
    }

    // Function return all products form table products
    @Override
    public List<Product> findAll() throws SQLException, ClassNotFoundException {
        query = "SELECT * FROM products";
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
}
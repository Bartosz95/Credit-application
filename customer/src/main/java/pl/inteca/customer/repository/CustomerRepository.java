package pl.inteca.customer.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.inteca.customer.api.Repository;
import pl.inteca.customer.domain.Customer;
import java.sql.SQLException;
import java.util.List;

@Configuration
public class CustomerRepository implements Repository<Customer, Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void createTable() throws ClassNotFoundException, SQLException {
        String query = "CREATE TABLE IF NOT EXISTS customers (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "firstName VARCHAR(255)," +
                "lastName VARCHAR(255)," +
                "personalId BIGINT," +
                "PRIMARY KEY (id))";
        jdbcTemplate.execute(query);

    }

    @Override
    public Customer save(Customer customer) {
        System.out.println(customer.toString());
        String query = String.format("INSERT INTO customers (firstName, lastName, personalId)" +
                "VALUES ('%s', '%s', '%d')", customer.getFirstName(), customer.getLastName(), customer.getPersonalId());
        jdbcTemplate.update(query);
        query = "SELECT * FROM customers WHERE id=LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(query, new CustomerRowMapper());
    }

    @Override
    public List<Customer> findAll() {
        String query = "SELECT * FROM customers";
        return jdbcTemplate.query(query, new CustomerRowMapper());
    }
}

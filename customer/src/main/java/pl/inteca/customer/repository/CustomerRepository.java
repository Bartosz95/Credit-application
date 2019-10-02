package pl.inteca.customer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.inteca.customer.api.CustomerRepoApi;
import pl.inteca.customer.domain.Customer;
import java.util.List;

@Configuration
public class CustomerRepository implements CustomerRepoApi {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS customers (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "firstName VARCHAR(255)," +
                "lastName VARCHAR(255)," +
                "personalId BIGINT," +
                "PRIMARY KEY (id))";
        try {
            for(int i = 10; i>0; i--){
                System.out.println(String.format("wait for database: %d second", i));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            jdbcTemplate.execute(query);
            System.out.println("Good connect to database");
        } catch (Exception e){
            System.out.println(String.format("Database server not response. Error: \n %s", e.getMessage()));
        }
    }

    @Override
    public Customer createCustomer(Customer customer) {
        String query = String.format("INSERT INTO customers (firstName, lastName, personalId)" +
                "VALUES ('%s', '%s', '%d')", customer.getFirstName(), customer.getLastName(), customer.getPersonalId());
        jdbcTemplate.update(query);
        query = "SELECT * FROM customers WHERE id=LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(query, new CustomerRowMapper());
    }

    @Override
    public List<Customer> getCustomers(List<Long> idList) {
        // Loop for query like "SELECT * FROM customers WHERE id IN(1,2)"
        StringBuilder query = new StringBuilder("SELECT * FROM customers WHERE id IN(");
        for(Long id : idList){
            query.append(id);
            query.append(",");
        }
        query.deleteCharAt(query.lastIndexOf(","));
        query.append(")");
        return jdbcTemplate.query(query.toString(), new CustomerRowMapper());
    }
}

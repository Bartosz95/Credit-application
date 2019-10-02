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

    /*
     * Function createTable() create table "customer" in database
     * customer table have (id, firstName, lastName, personalId)
     * IN: -
     * OUT -
     */
    @Autowired
    public void createTable() throws InterruptedException {
        String query = "CREATE TABLE IF NOT EXISTS customers (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "firstName VARCHAR(255)," +
                "lastName VARCHAR(255)," +
                "personalId BIGINT," +
                "PRIMARY KEY (id))";
        jdbcTemplate.execute(query);
    }

    /*
     * Function save customer in credit-db.customer table
     * IN: Customer.class without id, database connection
     * OUT: The same Customer.class with id
     */
    @Override
    public Customer createCustomer(Customer customer) {
        String query = String.format("INSERT INTO customers (firstName, lastName, personalId)" +
                "VALUES ('%s', '%s', '%d')", customer.getFirstName(), customer.getLastName(), customer.getPersonalId());
        jdbcTemplate.update(query);
        query = "SELECT * FROM customers WHERE id=LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(query, new CustomerRowMapper());
    }

    /*
     * Function return all products form table products
     * It send request to database looks like:
     * SELECT * FROM customer WHERE id IN(1,2,3,4)
     * and database send response like result list.
     * IN: List of id in long value, database connection
     * OUT: List of Customer.class
     */
    @Override
    public List<Customer> getCustomers(List<Long> idList) {
        // Prepare query string
        StringBuilder query = new StringBuilder("SELECT * FROM customers WHERE id IN(");
        for(Long id : idList){
            query.append(id);
            query.append(",");
        }
        query.deleteCharAt(query.lastIndexOf(","));
        query.append(")");
        // Send query and return Customer List
        return jdbcTemplate.query(query.toString(), new CustomerRowMapper());
    }
}

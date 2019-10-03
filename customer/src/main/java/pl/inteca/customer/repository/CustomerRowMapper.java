package pl.inteca.customer.repository;

import org.springframework.jdbc.core.RowMapper;
import pl.inteca.customer.domain.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerRowMapper implements RowMapper<Customer> {

    /*
     * Customer mapper is a function which convert the row into an object via row mapper
     * IN: Result set from database and number of row
     * OUT Customer object with set all value
     */
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Customer.builder() // used builder to create new object
                .id(rs.getLong("id"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .personalId(rs.getLong("personalId"))
                .build();
    }
}

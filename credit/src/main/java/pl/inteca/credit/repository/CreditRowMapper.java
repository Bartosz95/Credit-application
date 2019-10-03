package pl.inteca.credit.repository;

import pl.inteca.credit.domain.Credit;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditRowMapper implements RowMapper<Credit> {

    /*
     * Credit mapper is a function which convert the row into an object via row mapper
     * IN: Result set from database and number of row
     * OUT Credit object with set all value
     */
    @Override
    public Credit mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Credit.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .customerId(rs.getLong("customerId"))
                .productId(rs.getLong("productId"))
                .build();
    }
}

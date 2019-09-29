package bolo.spring.creditapplication.repository;

import bolo.spring.creditapplication.domain.Credit;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditRowMapper implements RowMapper<Credit> {

    @Override
    public Credit mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Credit.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}

package bolo.spring.creditapplication.repository;

import bolo.spring.creditapplication.api.Repository;
import bolo.spring.creditapplication.domain.Credit;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;

@Data
@NoArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class CreditRepository implements Repository<Credit, Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Function createTable() create table "product" in database
    @Autowired
    public void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS credits (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "PRIMARY KEY (id))";
        jdbcTemplate.execute(query);
    }

    @Override
    public Credit save(Credit credit) {

        String query = String.format("INSERT INTO credits (name) " +
                    "VALUES ('%s')", credit.getName());
        jdbcTemplate.update(query);
        query = "SELECT * FROM credits WHERE id=LAST_INSERT_ID()"; // get new credit id
        return jdbcTemplate.queryForObject(query, new CreditRowMapper());
    }

    @Override
    public List<Credit> findAll() {
        String query = "SELECT * FROM credits";
        return jdbcTemplate.query(query, new CreditRowMapper());
    }
}

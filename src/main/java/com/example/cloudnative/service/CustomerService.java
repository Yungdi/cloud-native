package com.example.cloudnative.service;

import com.example.cloudnative.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomerService {
    private final JdbcTemplate jdbcTemplate;

    public CustomerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Customer> findAll() {
        RowMapper<Customer> rowMapper = (rs, i) -> new Customer(rs.getLong("id"),
                rs.getString("email"));
        return jdbcTemplate.query("select * from CUSTOMERS", rowMapper);
    }


}

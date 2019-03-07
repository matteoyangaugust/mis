package com.matt.mapper;

import com.matt.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setSn(resultSet.getInt("sn"));
        product.setName(resultSet.getString("name"));
        product.setIdentifier(resultSet.getString("identifier"));
        product.setRemain(resultSet.getInt("remain"));
        product.setActive(resultSet.getInt("active"));
        return product;
    }
}

package com.matt.mapper;

import com.matt.model.Product_flowing_history;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductFlowingHistoryMapper implements RowMapper<Product_flowing_history> {
    @Override
    public Product_flowing_history mapRow(ResultSet resultSet, int i) throws SQLException {
        Product_flowing_history history = new Product_flowing_history();
        history.setSn(resultSet.getInt("sn"));
        history.setProduct_sn(resultSet.getInt("product_sn"));
        history.setAmount(resultSet.getInt("amount"));
        history.setUpdate_time(resultSet.getString("update_time"));
        history.setRemain(resultSet.getInt("remain"));
        history.setAction(resultSet.getInt("action"));
        return history;
    }
}

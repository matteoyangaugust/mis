package com.matt.mapper;

import com.matt.model.Component;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentMapper implements RowMapper<Component> {
    @Override
    public Component mapRow(ResultSet resultSet, int i) throws SQLException {
        Component component = new Component();
        component.setSn(resultSet.getInt("sn"));
        component.setIdentifier(resultSet.getString("identifier"));
        component.setName(resultSet.getString("name"));
        component.setProcess_category_sn(resultSet.getInt("process_category_sn"));
        component.setProcess_duration(resultSet.getInt("process_duration"));
        component.setPurchase_fee(resultSet.getDouble("purchase_fee"));
        component.setRemain(resultSet.getInt("remain"));
        component.setActive(resultSet.getInt("active"));
        component.setProcess_yield(resultSet.getInt("process_yield"));
        return component;
    }
}

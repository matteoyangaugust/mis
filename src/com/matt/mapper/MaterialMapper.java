package com.matt.mapper;

import com.matt.model.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialMapper implements RowMapper<Material> {
    @Override
    public Material mapRow(ResultSet resultSet, int i) throws SQLException {
        Material material = new Material();
        material.setSn(resultSet.getInt("sn"));
        material.setIdentifier(resultSet.getString("identifier"));
        material.setName(resultSet.getString("name"));
        material.setQuantity(resultSet.getDouble("quantity"));
        material.setLast_update_time(resultSet.getString("last_update_time"));
        material.setPrice(resultSet.getDouble("price"));
        material.setSupplier(resultSet.getInt("supplier"));
        material.setUnit(resultSet.getInt("unit"));
        material.setActive(resultSet.getInt("active"));
        return material;
    }
}

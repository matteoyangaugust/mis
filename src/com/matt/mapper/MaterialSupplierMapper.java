package com.matt.mapper;

import com.matt.model.Material_supplier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialSupplierMapper implements RowMapper<Material_supplier> {
    @Override
    public Material_supplier mapRow(ResultSet resultSet, int i) throws SQLException {
        Material_supplier supplier = new Material_supplier();
        supplier.setSn(resultSet.getInt("sn"));
        supplier.setName(resultSet.getString("name"));
        return supplier;
    }
}

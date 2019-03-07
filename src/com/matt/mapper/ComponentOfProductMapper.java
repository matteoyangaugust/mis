package com.matt.mapper;

import com.matt.model.Component_of_product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentOfProductMapper implements RowMapper<Component_of_product> {
    @Override
    public Component_of_product mapRow(ResultSet resultSet, int i) throws SQLException {
        Component_of_product componentsOfProduct = new Component_of_product();
        componentsOfProduct.setSn(resultSet.getInt("sn"));
        componentsOfProduct.setProduct_sn(resultSet.getInt("product_sn"));
        componentsOfProduct.setComponent_sn(resultSet.getInt("component_sn"));
        componentsOfProduct.setComponent_amount(resultSet.getInt("component_amount"));
        return componentsOfProduct;
    }
}

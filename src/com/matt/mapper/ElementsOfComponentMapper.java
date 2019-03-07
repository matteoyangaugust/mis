package com.matt.mapper;

import com.matt.model.Elements_of_component;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ElementsOfComponentMapper implements RowMapper<Elements_of_component> {
    @Override
    public Elements_of_component mapRow(ResultSet resultSet, int i) throws SQLException {
        Elements_of_component elementsOfComponent = new Elements_of_component();
        elementsOfComponent.setSn(resultSet.getInt("sn"));
        elementsOfComponent.setElement_sn(resultSet.getInt("element_sn"));
        elementsOfComponent.setComponent_sn(resultSet.getInt("component_sn"));
        elementsOfComponent.setElement_amount(resultSet.getDouble("element_amount"));
        elementsOfComponent.setElements_type(resultSet.getInt("elements_type"));
        return elementsOfComponent;
    }
}

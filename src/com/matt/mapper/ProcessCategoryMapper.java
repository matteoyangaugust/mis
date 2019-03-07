package com.matt.mapper;

import com.matt.model.Process_category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcessCategoryMapper implements RowMapper<Process_category>{

    @Override
    public Process_category mapRow(ResultSet resultSet, int i) throws SQLException {
        Process_category processCategory = new Process_category();
        processCategory.setSn(resultSet.getInt("sn"));
        processCategory.setName(resultSet.getString("name"));
        processCategory.setRelation(resultSet.getString("relation"));
        return processCategory;
    }
}

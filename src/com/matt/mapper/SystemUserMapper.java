package com.matt.mapper;

import com.matt.model.System_user;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemUserMapper implements RowMapper<System_user> {
    @Override
    public System_user mapRow(ResultSet resultSet, int i) throws SQLException {
        System_user user = new System_user();
        user.setSn(resultSet.getInt("sn"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setRole(resultSet.getString("role"));
        return user;
    }
}

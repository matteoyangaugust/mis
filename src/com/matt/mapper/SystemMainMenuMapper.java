package com.matt.mapper;

import com.matt.model.System_main_menu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemMainMenuMapper implements RowMapper<System_main_menu> {
    @Override
    public System_main_menu mapRow(ResultSet resultSet, int i) throws SQLException {
        System_main_menu mainMenu = new System_main_menu();
        mainMenu.setSn(resultSet.getInt("sn"));
        mainMenu.setName(resultSet.getString("name"));
        mainMenu.setCategory(resultSet.getString("category"));
        mainMenu.setIs_default(resultSet.getInt("is_default"));
        return mainMenu;
    }
}

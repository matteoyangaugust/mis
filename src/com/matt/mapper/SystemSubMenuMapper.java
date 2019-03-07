package com.matt.mapper;

import com.matt.model.System_sub_menu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemSubMenuMapper implements RowMapper<System_sub_menu>{
    @Override
    public System_sub_menu mapRow(ResultSet resultSet, int i) throws SQLException {
        System_sub_menu subMenu = new System_sub_menu();
        subMenu.setSn(resultSet.getInt("sn"));
        subMenu.setMain_menu_sn(resultSet.getInt("main_menu_sn"));
        subMenu.setName(resultSet.getString("name"));
        subMenu.setUrl(resultSet.getString("url"));
        subMenu.setRoles(resultSet.getString("roles"));
        subMenu.setIs_default(resultSet.getInt("is_default"));
        return subMenu;
    }
}

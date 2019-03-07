package com.matt.mapper;

import com.matt.model.Component_flowing_history;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentFlowingHistoryMapper implements RowMapper<Component_flowing_history>{
    @Override
    public Component_flowing_history mapRow(ResultSet resultSet, int i) throws SQLException {
        Component_flowing_history history = new Component_flowing_history();
        history.setSn(resultSet.getInt("sn"));
        history.setComponent_sn(resultSet.getInt("component_sn"));
        history.setProcess_category_sn(resultSet.getInt("process_category_sn"));
        history.setAmount(resultSet.getInt("amount"));
        history.setUpdate_time(resultSet.getString("update_time"));
        history.setPurchase_fee(resultSet.getDouble("purchase_fee"));
        history.setTotal_duration(resultSet.getInt("total_duration"));
        history.setRemain(resultSet.getInt("remain"));
        history.setProcess_amount(resultSet.getInt("process_amount"));
        return history;
    }
}

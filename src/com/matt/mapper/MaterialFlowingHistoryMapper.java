package com.matt.mapper;

import com.matt.model.Material_flowing_history;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialFlowingHistoryMapper implements RowMapper<Material_flowing_history> {
    @Override
    public Material_flowing_history mapRow(ResultSet resultSet, int i) throws SQLException {
        Material_flowing_history history = new Material_flowing_history();
        history.setSn(resultSet.getInt("sn"));
        history.setMaterial_sn(resultSet.getInt("material_sn"));
        history.setBuying_time(resultSet.getString("buying_time"));
        history.setUpdate_time(resultSet.getString("update_time"));
        history.setSupplier_sn(resultSet.getInt("supplier_sn"));
        history.setQuantity(resultSet.getDouble("quantity"));
        history.setRemain(resultSet.getDouble("remain"));
        history.setFee(resultSet.getDouble("fee"));
        return history;
    }
}

package com.matt.repository.impl;

import com.matt.mapper.MaterialFlowingHistoryMapper;
import com.matt.model.Material_flowing_history;
import com.matt.repository.BaseRepository;
import com.matt.repository.MaterialFlowingHistoryRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialFlowingHistoryRepositoryImpl extends BaseRepository implements MaterialFlowingHistoryRepository {
    private final XMLReader reader = XMLReader.getInstance(MaterialFlowingHistoryRepositoryImpl.class);
    @Override
    public Integer insert(Material_flowing_history history) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("material_flowing_history").usingColumns(super.getBeanPropertiesArr(history, "sn"));
        SqlParameterSource source = new BeanPropertySqlParameterSource(history);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public Integer update(Material_flowing_history history) {
        String sql = super.getUpdateScriptByPk("material_flowing_history", "sn", history);
        SqlParameterSource source = new BeanPropertySqlParameterSource(history);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer delete(Integer sn) {
        String sql = reader.getString("delete_history");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public List<Material_flowing_history> findMaterialFlowingHistories() {
        String sql = reader.getString("select_histories");
        try{
            return misDao.getJdbcTemplate().query(sql, new MaterialFlowingHistoryMapper());
        }catch (EmptyResultDataAccessException e){
            log.error("沒有任何原料進貨歷史紀錄", e);
            return null;
        }
    }

    @Override
    public Material_flowing_history findMaterialFlowingHistory(Integer sn) {
        String sql = reader.getString("select_history_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        try{
            return misDao.getJdbcTemplate().queryForObject(sql, source, new MaterialFlowingHistoryMapper());
        }catch (Exception e){
            log.error("Sn：" + sn + ", 沒有找到Material_flowing_history的結果");
            return null;
        }
    }
}

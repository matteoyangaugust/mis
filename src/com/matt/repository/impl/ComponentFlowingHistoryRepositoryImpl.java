package com.matt.repository.impl;

import com.matt.mapper.ComponentFlowingHistoryMapper;
import com.matt.model.Component_flowing_history;
import com.matt.model.System_user;
import com.matt.repository.BaseRepository;
import com.matt.repository.ComponentFlowingHistoryRepository;
import com.matt.service.MisService;
import com.matt.util.XMLReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComponentFlowingHistoryRepositoryImpl extends BaseRepository implements ComponentFlowingHistoryRepository {

    private final XMLReader reader = XMLReader.getInstance(ComponentFlowingHistoryRepositoryImpl.class);
    @Autowired
    private MisService misService;
    @Override
    public Integer insert(Component_flowing_history flowingHistory) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("component_flowing_history")
                .usingColumns(super.getBeanPropertiesArr(flowingHistory, "sn"));
        SqlParameterSource source = new BeanPropertySqlParameterSource(flowingHistory);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public List<Component_flowing_history> findFlowingHistories(System_user user) {
        String sql = reader.getString("select_flowing_histories_by_process_category_sn");
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("process_category_sn", misService.getProcessCategorySnByUser(user));
        return misDao.getJdbcTemplate().query(sql, source, new ComponentFlowingHistoryMapper());
    }

    @Override
    public Integer delete(Integer sn) {
        String sql = reader.getString("delete_flowing_history_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer update(Component_flowing_history history) {
        String sql = super.getUpdateScriptByPk("component_flowing_history", "sn", history);
        SqlParameterSource source = new BeanPropertySqlParameterSource(history);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public List<Component_flowing_history> findFlowingHistoriesByComponent(Integer component_sn) {
        String sql = reader.getString("select_flowing_histories_by_component_sn");
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("component_sn", component_sn);
        return misDao.getJdbcTemplate().query(sql, source, new ComponentFlowingHistoryMapper());
    }
}

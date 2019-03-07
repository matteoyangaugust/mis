package com.matt.repository.impl;

import com.matt.mapper.ComponentMapper;
import com.matt.model.Component;
import com.matt.repository.BaseRepository;
import com.matt.repository.ComponentRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComponentRepositoryImpl extends BaseRepository implements ComponentRepository{

    private final XMLReader reader = XMLReader.getInstance(ComponentRepositoryImpl.class);

    @Override
    public Integer insert(Component component) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("component")
                .usingColumns(super.getBeanPropertiesArr(component, "sn"));
        SqlParameterSource source = new BeanPropertySqlParameterSource(component);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public List<Component> findComponents() {
        String sql = reader.getString("select_all_components");
        try{
            return misDao.getJdbcTemplate().query(sql, new ComponentMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Integer update(Component component) {
        String sql = super.getUpdateScriptByPk("component", "sn", component);
        SqlParameterSource source = new BeanPropertySqlParameterSource(component);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer delete(Integer sn) {
        String sql = reader.getString("delete_component_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Component findComponents(Integer component_sn) {
        String sql = reader.getString("select_component_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", component_sn);
        try{
            return misDao.getJdbcTemplate().queryForObject(sql, source, new ComponentMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}

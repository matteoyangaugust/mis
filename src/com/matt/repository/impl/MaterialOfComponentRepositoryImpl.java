package com.matt.repository.impl;

import com.matt.mapper.ElementsOfComponentMapper;
import com.matt.model.Elements_of_component;
import com.matt.repository.BaseRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MaterialOfComponentRepositoryImpl extends BaseRepository implements MaterialOfComponentRepository {
    private final XMLReader reader = XMLReader.getInstance(MaterialOfComponentRepositoryImpl.class);
    @Override
    public Integer insert(Elements_of_component elements_of_component) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("elements_of_component").usingColumns(super.getBeanPropertiesArr(elements_of_component, "sn"));
        SqlParameterSource source = new BeanPropertySqlParameterSource(elements_of_component);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public Integer delete(Integer component_sn) {
        String sql = reader.getString("delete_material_of_component_by_component_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("component_sn", component_sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer delete(List<Integer> component_sns) {
        return null;
    }

    @Override
    public List<Elements_of_component> findMaterialsOfComponent() {
        String sql = reader.getString("select_material_of_component");
        try{
            return misDao.getJdbcTemplate().query(sql, new ElementsOfComponentMapper());
        }catch (EmptyResultDataAccessException e){
            log.error("Table Elements_of_component is Empty");
            return null;
        }
    }

    @Override
    public List<Elements_of_component> findMaterialsOfComponent(Integer componentSn) {
        String sql = reader.getString("select_material_of_component_by_component_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("componentSn", componentSn);
        try{
            return misDao.getJdbcTemplate().query(sql, source, new ElementsOfComponentMapper());
        }catch (EmptyResultDataAccessException e){
            log.error("Component sn : " + componentSn + " has no material to use");
            return new ArrayList<>();
        }
    }

    @Override
    public Integer delete(Integer componentSn, Integer componentType) {
        String sql = reader.getString("delete_material_of_component_by_component_sn_and_type");
        SqlParameterSource source = new MapSqlParameterSource().addValue("component_sn", componentSn)
                .addValue("element_type", componentType);
        return misDao.getJdbcTemplate().update(sql, source);
    }
}

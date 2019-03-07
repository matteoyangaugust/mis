package com.matt.repository.impl;

import com.matt.mapper.MaterialMapper;
import com.matt.model.Material;
import com.matt.repository.BaseRepository;
import com.matt.repository.MaterialRepository;
import com.matt.util.XMLReader;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialRepositoryImpl extends BaseRepository implements MaterialRepository{
    private final XMLReader reader = XMLReader.getInstance(MaterialRepositoryImpl.class);
    @Override
    public Integer insert(Material material) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("material").usingColumns("identifier", "name", "quantity", "price", "last_update_time", "unit", "supplier");
        SqlParameterSource source = new BeanPropertySqlParameterSource(material);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public List<Material> findMaterials() {
        String sql = reader.getString("select_all_materials");
        return misDao.getJdbcTemplate().query(sql, new MaterialMapper());
    }

    @Override
    public Material findMaterials(Integer material_sn) {
        String sql = reader.getString("select_material_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", material_sn);
        return misDao.getJdbcTemplate().queryForObject(sql, source, new MaterialMapper());
    }

    @Override
    public List<Material> findMaterials(List<Integer> material_sn) {
        String sql = reader.getString("select_material_by_sns");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sns", material_sn);
        return misDao.getJdbcTemplate().query(sql, source, new MaterialMapper());
    }

    @Override
    public Integer update(Material material) {
        String sql = super.getUpdateScriptByPk("material", "sn", material);
        SqlParameterSource source = new BeanPropertySqlParameterSource(material);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer delete(Integer sn) {
        String sql = reader.getString("delete_material_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }
}

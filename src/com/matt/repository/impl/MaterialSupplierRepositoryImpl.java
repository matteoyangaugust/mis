package com.matt.repository.impl;

import com.matt.mapper.MaterialSupplierMapper;
import com.matt.model.Material_supplier;
import com.matt.repository.BaseRepository;
import com.matt.repository.MaterialSupplierRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialSupplierRepositoryImpl extends BaseRepository implements MaterialSupplierRepository{
    private final XMLReader reader = XMLReader.getInstance(MaterialSupplierRepositoryImpl.class);
    @Override
    public List<Material_supplier> findMaterialSuppliers() {
        String sql = reader.getString("select_all_material_supplier");
        try{
            return misDao.getJdbcTemplate().query(sql, new MaterialSupplierMapper());
        }catch (EmptyResultDataAccessException e){
            log.error("沒有任何廠商資料");
            return null;
        }
    }

    @Override
    public Integer insert(Material_supplier supplier) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().withTableName("material_supplier")
                .usingGeneratedKeyColumns("sn").usingColumns("name");
        SqlParameterSource source = new BeanPropertySqlParameterSource(supplier);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public Integer delete(Integer sn) {
        String sql = reader.getString("delete_supplier_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer update(Material_supplier supplierToUpdate) {
        String sql = super.getUpdateScriptByPk("material_supplier", "sn", supplierToUpdate);
        SqlParameterSource source = new BeanPropertySqlParameterSource(supplierToUpdate);
        return misDao.getJdbcTemplate().update(sql, source);
    }
}

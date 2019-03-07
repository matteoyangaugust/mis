package com.matt.repository.impl;

import com.matt.mapper.ComponentOfProductMapper;
import com.matt.model.Component_of_product;
import com.matt.repository.BaseRepository;
import com.matt.repository.ComponentsOfProductRepository;
import com.matt.util.XMLReader;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComponentsOfProductRepositoryImpl extends BaseRepository implements ComponentsOfProductRepository {

    private final XMLReader reader = XMLReader.getInstance(ComponentsOfProductRepositoryImpl.class);
    @Override
    public Integer insert(Component_of_product component_of_product) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("component_of_product").usingColumns(super.getBeanPropertiesArr(component_of_product, "sn"));
        SqlParameterSource source = new BeanPropertySqlParameterSource(component_of_product);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public List<Component_of_product> findComponentOdProducts() {
        String sql = reader.getString("select_all_components_of_product");
        return misDao.getJdbcTemplate().query(sql, new ComponentOfProductMapper());
    }

    @Override
    public Integer delete(List<Integer> componentSnList) {
        String sql = reader.getString("delete_component_of_product_by_component_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("component_sn", componentSnList);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer delete(Integer product_sn) {
        String sql = reader.getString("delete_component_of_product_by_product_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("product_sn", product_sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }
}

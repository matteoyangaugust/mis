package com.matt.repository.impl;

import com.matt.mapper.ProductMapper;
import com.matt.model.Product;
import com.matt.repository.BaseRepository;
import com.matt.repository.ProductRepository;
import com.matt.util.XMLReader;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl extends BaseRepository implements ProductRepository {

    private final XMLReader reader = XMLReader.getInstance(ProductRepositoryImpl.class);
    @Override
    public Integer insert(Product product) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("product").usingColumns(super.getBeanPropertiesArr(product, "sn"));
        SqlParameterSource source = new BeanPropertySqlParameterSource(product);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public List<Product> findProducts() {
        String sql = reader.getString("select_all_products");
        return misDao.getJdbcTemplate().query(sql, new ProductMapper());
    }

    @Override
    public int update(Product product) {
        String sql = super.getUpdateScriptByPk("product", "sn", product);
        SqlParameterSource source = new BeanPropertySqlParameterSource(product);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer delete(Integer sn) {
        String sql = reader.getString("delete_product_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Product findProducts(Integer product_sn) {
        String sql = reader.getString("select_product_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", product_sn);
        return misDao.getJdbcTemplate().queryForObject(sql, source, new ProductMapper());
    }
}

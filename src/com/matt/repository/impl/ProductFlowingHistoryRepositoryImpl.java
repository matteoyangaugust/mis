package com.matt.repository.impl;

import com.matt.mapper.ProductFlowingHistoryMapper;
import com.matt.model.Product_flowing_history;
import com.matt.repository.BaseRepository;
import com.matt.repository.ProductFlowingHistoryRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductFlowingHistoryRepositoryImpl extends BaseRepository implements ProductFlowingHistoryRepository{
    private final XMLReader reader = XMLReader.getInstance(ProductFlowingHistoryRepositoryImpl.class);


    @Override
    public List<Product_flowing_history> findHistories() {
        String sql = reader.getString("select_all_product_flowing_history");
        try{
            return misDao.getJdbcTemplate().query(sql, new ProductFlowingHistoryMapper());
        }catch (EmptyResultDataAccessException e){
            log.error("Product_flowing_history未找到資料" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Integer deleteBySn(Integer sn) {
        String sql = reader.getString("delete_product_flowing_history_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer insert(Product_flowing_history history) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert().usingGeneratedKeyColumns("sn")
                .withTableName("product_flowing_history").usingColumns(super.getBeanPropertiesArr(history, "sn"));
        SqlParameterSource source = new BeanPropertySqlParameterSource(history);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public Integer update(Product_flowing_history history) {
        String sql = super.getUpdateScriptByPk("product_flowing_history", "sn", history);
        SqlParameterSource source = new BeanPropertySqlParameterSource(history);
        return misDao.getJdbcTemplate().update(sql, source);
    }
}

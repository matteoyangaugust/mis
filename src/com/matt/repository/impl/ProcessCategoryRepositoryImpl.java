package com.matt.repository.impl;

import com.matt.mapper.ProcessCategoryMapper;
import com.matt.model.Process_category;
import com.matt.repository.BaseRepository;
import com.matt.repository.ProcessCategoryRepository;
import com.matt.util.XMLReader;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProcessCategoryRepositoryImpl extends BaseRepository implements ProcessCategoryRepository{
    private final XMLReader reader = XMLReader.getInstance(ProcessCategoryRepositoryImpl.class);
    @Override
    public List<Process_category> findProcessCategories() {
        String sql = reader.getString("select_all_process_categories");
        return misDao.getJdbcTemplate().query(sql, new ProcessCategoryMapper());
    }

    @Override
    public Process_category findProcessCategory(Integer process_category_sn) {
        String sql = reader.getString("select_process_category_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", process_category_sn);
        return misDao.getJdbcTemplate().queryForObject(sql, source, new ProcessCategoryMapper());
    }
}

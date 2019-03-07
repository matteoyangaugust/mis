package com.matt.repository.impl;

import com.matt.mapper.SystemSubMenuMapper;
import com.matt.model.System_sub_menu;
import com.matt.repository.BaseRepository;
import com.matt.repository.SystemSubMenuRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SystemSubMenuRepositoryImpl extends BaseRepository implements SystemSubMenuRepository{
    private final XMLReader reader = XMLReader.getInstance(SystemSubMenuRepositoryImpl.class);
    @Override
    public List<System_sub_menu> findSubMenus() {
        String sql = reader.getString("select_all_sub_menus");
        try{
            return misDao.getJdbcTemplate().query(sql, new SystemSubMenuMapper());
        }catch (EmptyResultDataAccessException e){
            log.error("沒有任何子選單");
            return null;
        }
    }
}

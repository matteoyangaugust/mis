package com.matt.repository.impl;

import com.matt.mapper.SystemMainMenuMapper;
import com.matt.model.System_main_menu;
import com.matt.repository.BaseRepository;
import com.matt.repository.SystemMainMenuRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SystemMainMenuRepositoryImpl extends BaseRepository implements SystemMainMenuRepository{
    private final XMLReader reader = XMLReader.getInstance(SystemMainMenuRepositoryImpl.class);
    @Override
    public List<System_main_menu> findMainMenu() {
        try{
            return misDao.getJdbcTemplate().query(
                    reader.getString("select_all_main_menus"),
                    new SystemMainMenuMapper());
        }catch (EmptyResultDataAccessException e){
            log.error("沒有任何的主選單");
            return null;
        }
    }
}

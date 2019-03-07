package com.matt.repository.impl;

import com.matt.mapper.SystemUserMapper;
import com.matt.model.System_user;
import com.matt.repository.BaseRepository;
import com.matt.repository.SystemUserRepository;
import com.matt.util.XMLReader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class SystemUserRepositoryImpl extends BaseRepository implements SystemUserRepository {
    private final XMLReader reader = XMLReader.getInstance(SystemUserRepositoryImpl.class);
    @Override
    public Integer insertSystemUser(System_user user) {
        SimpleJdbcInsert insert = misDao.getJdbcInsert()
                .withTableName("system_user").usingGeneratedKeyColumns("sn")
                .usingColumns("username", "password", "name", "role");
        SqlParameterSource source = new BeanPropertySqlParameterSource(user);
        return insert.executeAndReturnKey(source).intValue();
    }

    @Override
    public List<System_user> findUsers() {
        String sql = reader.getString("select_all_system_users");
        try{
            return misDao.getJdbcTemplate().query(sql, new SystemUserMapper());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public System_user findUserByUsername(String username) {
        String sql = reader.getString("select_system_user_by_username");
        SqlParameterSource bindValues = new MapSqlParameterSource()
                .addValue("username", username);
        try{
            return misDao.getJdbcTemplate().queryForObject(sql, bindValues, new SystemUserMapper());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<System_user> findUserByRole(String role) {
        String sql = reader.getString("select_system_user_by_role");
        SqlParameterSource bindValues = new MapSqlParameterSource()
                .addValue("role", role);
        try{
            return misDao.getJdbcTemplate().query(sql, bindValues, new SystemUserMapper());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<System_user> findUserBySn(Set<Integer> sn) {
        String sql = reader.getString("select_system_user_by_several_sn");
        SqlParameterSource bindValues = new MapSqlParameterSource().addValue("sn", sn);
        try{
            return misDao.getJdbcTemplate().query(sql, bindValues, new SystemUserMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Integer update(System_user user) {
        String sql = super.getUpdateScriptByPk("system_user", "sn", user);
        SqlParameterSource source = new BeanPropertySqlParameterSource(user);
        return misDao.getJdbcTemplate().update(sql, source);
    }

    @Override
    public Integer delete(Integer sn) {
        String sql = reader.getString("delete_system_user_by_sn");
        SqlParameterSource source = new MapSqlParameterSource().addValue("sn", sn);
        return misDao.getJdbcTemplate().update(sql, source);
    }
}

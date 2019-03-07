package com.matt.dataAccess;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.io.Serializable;

public class JdbcConnector implements Serializable{
	private ApplicationContext ctx = null;
	private NamedParameterJdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private final DataSource dataSource;
	private final String qualifier;
	private boolean isShowSql;
	private boolean isShowStats;

	public SimpleJdbcInsert getJdbcInsert() {
		return new SimpleJdbcInsert(dataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public String getQualifier() {
		return qualifier;
	}

	public boolean isShowSql() {
		return isShowSql;
	}

	public void setShowSql(boolean showSql) {
		isShowSql = showSql;
	}

	public boolean isShowStats() {
		return isShowStats;
	}

	public void setShowStats(boolean showStats) {
		isShowStats = showStats;
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public JdbcConnector(String qualifier, DataSource dataSource){
		this.qualifier = qualifier;
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}

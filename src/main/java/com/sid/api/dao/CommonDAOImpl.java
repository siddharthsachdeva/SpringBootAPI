package com.sid.api.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CommonDAOImpl implements CommonDAO   {


	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean validate(String sqlQuery, Object[] params) throws Exception {
		boolean validationResult = false;
		try{
		List<Map<String, Object>> data = jdbcTemplate.queryForList(sqlQuery, params);
		if(data.size() > 0){
			validationResult = true;
		}
		}catch(DataAccessException dae){
			throw new Exception();
		}
		return validationResult;	
	}
	
	//Method to validate that the given query results any data or not.
	@Override
	public boolean validate(String sqlQuery) {
		boolean validationResult = false;
		try{
		List<Map<String, Object>> data = jdbcTemplate.queryForList(sqlQuery);
		if(data.size() > 0){
			validationResult = true;
		}
		}catch(DataAccessException dae){
			
		}
		return validationResult;	
	}
	
	//Method to perform updation (insertion, deletion, modification) operation on database
	@Override
	public int update(String sqlQuery) throws Exception  {
		int noOfRowsAffected = 0;
		try{
			noOfRowsAffected = jdbcTemplate.update(sqlQuery);	
		}catch(DataAccessException dae){
			dae.printStackTrace();
			throw dae;
		}
		return noOfRowsAffected;
	}
	
	//Method to fetch a string from database query.
	@Override
	public List<Map<String, Object>> fetch(String sqlQuery)  {
		List<Map<String, Object>> data = new ArrayList<>();
		try{
			data = jdbcTemplate.queryForList(sqlQuery);
		}catch(DataAccessException dae){
			dae.printStackTrace();
			
		}
		return data;
	}
	
	//Method to fetch whole table data for the given query and parameters.
	@Override
	public List<Map<String, Object>> fetch(String sqlQuery, Object[] params) {
		List<Map<String, Object>> data = new ArrayList<>();
		try{
			data = jdbcTemplate.queryForList(sqlQuery, params);
		}catch(DataAccessException dae){
			
		}
		return data;
	}

	//Overloaded method to perform updation operation.
	@Override
	public int update(String sqlQuery, Object[] params) throws Exception {
		int noOfRowsAffected = 0;
		try{
			noOfRowsAffected = jdbcTemplate.update(sqlQuery, params);
		}catch(DuplicateKeyException dke){
			dke.printStackTrace();
			throw new Exception();
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			throw new Exception();
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return noOfRowsAffected;
	}

	//Fetching one column's data.
	@Override
	public List<String> fetchDataList(String dataListQuery, Object[] params) {
		List<String> dataList = new ArrayList<>();
		try {
			dataList = jdbcTemplate.queryForList(dataListQuery, params, String.class);
		} catch (DataAccessException dae) {
			
		}
		return dataList;
	}
	// Overloaded method to fetch string.
	@Override
	public String fetchString(String stringQuery, Object[] params) {
		List<String> dataList = new ArrayList<>();
		String string = null;
		try {
			dataList = jdbcTemplate.queryForList(stringQuery, params, String.class);
			string = dataList.get(0);
		} catch (DataAccessException dae) {
			
		}
		return string;
	}

	//Overloaded method to fetch one column's data.
	@Override
	public List<String> fetchDataList(String dataListQuery) {
		List<String> dataList = new ArrayList<>();
		try {
			dataList = jdbcTemplate.queryForList(dataListQuery, String.class);
		} catch (DataAccessException dae) {
			
		}
		return dataList;
	}
	
}

package com.sid.api.dao;

import java.util.List;
import java.util.Map;

public interface CommonDAO {

	int update(String sqlQuery) throws Exception;

	int update(String sqlQuery, Object[] params) throws Exception;
	
	List<Map<String, Object>> fetch(String sqlQuery);

	List<Map<String, Object>> fetch(String sqlQuery, Object[] params);

	boolean validate(String sqlQuery, Object[] params) throws Exception;

	List<String> fetchDataList(String dataListQuery, Object[] params);

	boolean validate(String sqlQuery);

	List<String> fetchDataList(String dataListQuery);

	String fetchString(String stringQuery, Object[] params);

}

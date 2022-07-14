package com.sid.api.services;

import java.util.List;
import java.util.Map;

public interface CategoryService {
	
	Map<String, String> addCategory(Map<String, Object> request);
	List<Map<String, Object>> fetchAllCategories();
	Map<String, Object> removeCategories(List<Map<String, String>> request);
	Map<String, String> updateCategory(Map<String, Object> request);
	Map<String, String> addCategories(List<Map<String, Object>> request);

}

package com.sid.api.services;

import java.util.List;
import java.util.Map;

public interface UserService {
	
	Map<String, String> registerUserService(Map<String, String> requestMap);

	Map<String, String> deactivateUser(Map<String, String> requestMap);

	List<Map<String, Object>> loginUser(Map<String, String> requestMap);

	List<Map<String, Object>> fetchAllUsers();

	Map<String, String> toggleAccountStatus(Map<String, String> request);

	Map<String, String> toggleOptOutStatus(Map<String, String> request);

	Map<String, String> updateUserCountry(Map<String, String> request);

	String fetchAllCountries();

}

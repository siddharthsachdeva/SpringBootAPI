package com.sid.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sid.api.utility.Constants;
import com.sid.api.utility.Countries;
import com.sid.api.utility.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.api.dao.CommonDAO;
import com.sid.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CommonDAO commonDao;

	@Transactional
	@Override
	public Map<String, String> registerUserService(Map<String, String> requestMap) {
		
		Map<String, String> response = new HashMap<>();
		String validateUserSQLQuery = SQLQueries.VALIDATE_USER_QUERY;
		try {
			
			String email = requestMap.get(Constants.EMAIL) == null ? "" : requestMap.get(Constants.EMAIL);
			
			Object[] emailParams = {email}; 
			
			if(commonDao.validate(validateUserSQLQuery, emailParams)){
				
				String userStatusAndUserIdQuery = SQLQueries.USER_STATUS_AND_USER_ID_QUERY;
				List<Map<String, Object>> accountStatusAndIdMap = commonDao.fetch(userStatusAndUserIdQuery, emailParams);
				System.out.println("User is already registered");
				String accountStatus = accountStatusAndIdMap.get(0).get(Constants.IS_ACTIVE).toString();
				String userId = accountStatusAndIdMap.get(0).get(Constants.USER_ID).toString();
				
				response.put(Constants.USER_ID, userId);
				
				if(!accountStatus.equalsIgnoreCase(Constants.ACTIVE)){
					response.put(Constants.STATUS, Constants.ERROR);
					response.put(Constants.MESSAGE, Constants.USER_ACCOUNT_DEACTIVATED);
					System.out.println("User account is deactivated");
					return response;
				}
				
				response.put(Constants.STATUS, Constants.OK);
				response.put(Constants.MESSAGE, Constants.USER_ALREADY_REGISTERED);
				
				return response;
				
			}else{
				
				System.out.println("Registering new user.");
				
				String id = UUID.randomUUID().toString();
				String title = requestMap.get(Constants.TITLE);
				String firstName = requestMap.get(Constants.FIRST_NAME) == null ? "" : requestMap.get(Constants.FIRST_NAME);
				String lastName = requestMap.get(Constants.LAST_NAME) == null ? "" : requestMap.get(Constants.LAST_NAME);
				String phoneNo = requestMap.get(Constants.PHONE_NO) == null ? "" : requestMap.get(Constants.PHONE_NO);
				String dateOfBirth = requestMap.get(Constants.DATE_OF_BIRTH) == null ? "" : requestMap.get(Constants.DATE_OF_BIRTH);
				String fbUserId = requestMap.get(Constants.FB_USER_ID) == null ? "" : requestMap.get(Constants.FB_USER_ID);
				String gender = requestMap.get(Constants.GENDER) == null ? "" : requestMap.get(Constants.GENDER);
				String country = requestMap.get(Constants.COUNTRY) == null ? "" : requestMap.get(Constants.COUNTRY);
				String state = requestMap.get(Constants.STATE) == null ? "" : requestMap.get(Constants.STATE);
				String city = requestMap.get(Constants.CITY) == null ? "" : requestMap.get(Constants.CITY);
				String zipcode = requestMap.get(Constants.ZIPCODE) == null ? "" : requestMap.get(Constants.ZIPCODE);
				String deviceToken = requestMap.get(Constants.DEVICE_TOKEN) == null ? "" : requestMap.get(Constants.DEVICE_TOKEN);
				String type = requestMap.get(Constants.TYPE) == null ? "" : requestMap.get(Constants.TYPE);
				
				
				if(type ==  null || firstName == null){
					response.put(Constants.STATUS, Constants.ERROR);
					response.put(Constants.MESSAGE, Constants.INVALID_REQUEST);
					System.out.println("Invalid request");
					return response;
				}

				String sqlQueryInsertUserDetails = SQLQueries.INSERT_INTO_APP_USER_DETAILS_QUERY;
				Object[] params = { id, title, firstName, lastName, email, phoneNo, dateOfBirth, fbUserId, gender, country,
						state, city, zipcode, deviceToken };

				commonDao.update(sqlQueryInsertUserDetails, params);
				String sqlQueryInsertUserType = SQLQueries.INSERT_INTO_APP_USER_TYPE_QUERY;

				Object[] paramsForUserType = { id, type };

				int no2 = commonDao.update(sqlQueryInsertUserType, paramsForUserType);
				
				response.put(Constants.USER_ID, id);
				
			}
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.USER_REGISTERED_SUCCESSFULLY);
		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}

		return response;
	}

	@Override
	public Map<String, String> deactivateUser(Map<String, String> requestMap) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.DEACTIVATE_USER_QUERY;
		try{
			String userId = requestMap.get(Constants.USER_ID);
			Object[] params = {userId};
			commonDao.update(sqlQuery, params);
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.USER_DEACTIVATED_SUCCESSFULLY);
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}
	
	//previously it was reactivateUser
	@Override
	public Map<String, String> toggleAccountStatus(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.TOGGLE_USER_ACCOUNT_STATUS_QUERY;
		try{
			String userId = request.get(Constants.USER_ID);
			Object[] params = {userId};
			commonDao.update(sqlQuery, params);	
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.USERS_ACCOUNT_STATUS_UPDATED_SUCCESSFULLY);
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}
	
	@Override
	public List<Map<String, Object>> loginUser(Map<String, String> requestMap) {
		List<Map<String, Object>>  response = null;
		String sqlQuery = SQLQueries.LOGIN_USER_QUERY;
		try{
			String userId = requestMap.get(Constants.USER_ID);
			Object[] params = {userId};
			response = commonDao.fetch(sqlQuery, params);
		}catch(Exception e){
			Map<String, Object> errorResponse = new HashMap<>();	
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			response.add(errorResponse);
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> fetchAllUsers() {
		List<Map<String, Object>>  response = null;
		String sqlQuery = SQLQueries.FETCH_ALL_USERS_QUERY;
		
		try{	
			response = commonDao.fetch(sqlQuery);
		}catch(Exception e){
			Map<String, Object> errorResponse = new HashMap<>();	
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			response.add(errorResponse);
		}
		
		return response;
	}

	@Override
	public Map<String, String> toggleOptOutStatus(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.TOGGLE_OPT_OUT_STATUS_QUERY;
		try{
			String userId = request.get(Constants.USER_ID);
			Object[] params = {userId};
			commonDao.update(sqlQuery, params);	
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.USERS_OPT_OUT_STATUS_UPDATED_SUCCESSFULLY);
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}

	@Override
	public Map<String, String> updateUserCountry(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.UPDATE_USER_COUNTRY_QUERY;
		String validateUserSQLQuery = SQLQueries.VALIDATE_USER_ID_QUERY;
		try{
			String userId = request.get(Constants.USER_ID);
			Object[] validateParams = {userId};
			if(!commonDao.validate(validateUserSQLQuery, validateParams)){
				response.put(Constants.STATUS, Constants.OK);
				response.put(Constants.MESSAGE, Constants.USER_DOESNT_EXISTS);
				return response;
			}
			String country = request.get(Constants.COUNTRY);
			Object[] params = {country, userId};
			commonDao.update(sqlQuery, params);	
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.USERS_COUNTRY_UPDATED_SUCCESSFULLY);
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}

	@Override
	public String fetchAllCountries() {
		return Countries.ALL_COUNTRIES;
	}
	
}

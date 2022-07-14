package com.sid.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sid.api.utility.Constants;
import com.sid.api.utility.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.api.dao.CommonDAO;
import com.sid.api.services.AppFeedbackService;

@Service
public class AppFeedbackServiceImpl implements AppFeedbackService{
	
	@Autowired
	private CommonDAO commonDAO;

	@Transactional
	@Override
	public Map<String, String> addAppFeedback(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.INSERT_INTO_APP_APP_FEEDBACK_QUERY;
		try {
				String id = UUID.randomUUID().toString();
				String feedback = request.get(Constants.FEEDBACK);
				String type = request.get(Constants.TYPE);
				String createdDate = request.get(Constants.CREATED_DATE);
				String userID = request.get(Constants.USER_ID);

				Object[] params = { id, feedback, type, createdDate, userID };
				commonDAO.update(sqlQuery, params);
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.FEEDBACK_ADDED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;

	}

	@Override
	public List<Map<String, Object>> fetchAppFeedback() {
		List<Map<String, Object>> data = new ArrayList<>();
		String sqlQuery = SQLQueries.FETCH_ALL_APP_FEEDBACK_QUERY;
		
		try{
			
			data = commonDAO.fetch(sqlQuery);
			
		}catch(Exception e){
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			
			data.add(errorResponse);
		}
		return data;
		
	}

}

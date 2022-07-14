package com.sid.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.api.dao.CommonDAO;
import com.sid.api.services.QuestionService;
import com.sid.api.utility.Constants;
import com.sid.api.utility.SQLQueries;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private CommonDAO commonDAO;

	@Transactional
	@Override
	public Map<String, String> addQuestions(List<Map<String, String>> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.INSERT_INTO_APP_QUESTIONS_QUERY;

		try {

			for (Map<String, String> data : request) {

				String id = UUID.randomUUID().toString();
				String questionDescription = data.get(Constants.QUESTION_DESCRIPTION);
				Object[] params = { id, questionDescription };
				commonDAO.update(sqlQuery, params);
			}
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.QUESTIONS_ADDED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}

		return response;
	}

	@Override
	public List<Map<String, Object>> fetchAllQuestions() {
		List<Map<String, Object>> data = new ArrayList<>();
		String sqlQuery = SQLQueries.FETCH_ALL_QUESTIONS_QUERY;
		
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

	@Override
	public Map<String, String> deleteQuestionById(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		try{

			String sqlQuery = SQLQueries.DELETE_QUESTION_QUERY;
			String questionId = request.get(Constants.QUESTION_ID);
			Object[] params = {questionId};
			commonDAO.update(sqlQuery, params);
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.QUESTION_DELETED_SUCCESSFULLY);
			
		}catch(Exception e){
			
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		
		return response;
	}

	@Override
	public Map<String, String> updateQuestionById(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.UPDATE_QUESTION_QUERY;
		try{			
			String questionId = request.get(Constants.QUESTION_ID);
			String description = request.get(Constants.DESCRIPTION);
			Object[] params = {description, questionId};
			commonDAO.update(sqlQuery, params);
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.QUESTION_UPDATED_SUCCESSFULLY);
			
		}catch(Exception e){
			
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		
		return response;
	}

	@Override
	public Map<String, String> deleteAllQuestions() {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.DELETE_ALL_QUESTIONS_QUERY;
		
		try{
			
			commonDAO.update(sqlQuery);
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.ALL_QUESTIONS_DELETED_SUCCESSFULLY);
			
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}
}

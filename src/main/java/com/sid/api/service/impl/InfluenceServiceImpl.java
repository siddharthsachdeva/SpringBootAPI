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
import com.sid.api.services.InfluenceService;

@Service
public class InfluenceServiceImpl implements InfluenceService {

	@Autowired
	private CommonDAO commonDAO;

	@Transactional
	@Override
	public Map<String, String> addInfluences(List<Map<String, String>> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.INSERT_INTO_INFLUENCES_QUERY;
		try {

			for (Map<String, String> data : request) {
				String influenceId = UUID.randomUUID().toString();
				String influence = data.get(Constants.INFLUENCE);
				String influenceImage = data.get(Constants.INFLUENCE_IMAGE);

				Object[] params = { influenceId, influence, influenceImage };
				commonDAO.update(sqlQuery, params);
			}
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.INFLUENCES_ADDED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}

	@Transactional
	@Override
	public Map<String, String> removeInfluences(List<Map<String, String>> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.DELETE_FROM_INFLUENCES_QUERY;
		try {

			for (Map<String, String> data : request) {
				String influenceId = data.get(Constants.INFLUENCE_ID);

				Object[] params = { influenceId };
				commonDAO.update(sqlQuery, params);
			}
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.INFLUENCES_REMOVED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}

	@Override
	public Map<String, String> updateInfluence(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.UPDATE_INFLUENCE_QUERY;
		try {

			String influenceId = request.get(Constants.INFLUENCE_ID);
			String influence = request.get(Constants.INFLUENCE);
			String influenceImage = request.get(Constants.INFLUENCE_IMAGE);

			Object[] params = { influence, influenceImage, influenceId };
			commonDAO.update(sqlQuery, params);

			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.INFLUENCE_UPDATED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> fetchAllInfluences() {
		List<Map<String, Object>> data = new ArrayList<>();
		String sqlQuery = SQLQueries.FETCH_ALL_INFLUENCES_QUERY;
		
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

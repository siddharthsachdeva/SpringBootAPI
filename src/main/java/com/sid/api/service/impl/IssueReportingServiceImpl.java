package com.sid.api.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sid.api.utility.Constants;
import com.sid.api.utility.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sid.api.dao.CommonDAO;
import com.sid.api.services.IssueReportingService;

@Service
public class IssueReportingServiceImpl implements IssueReportingService {
	
	@Autowired
	private CommonDAO commonDAO;

	@Override
	public Map<String, String> reportIssue(Map<String, String> request) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.REPORT_ISSUE_QUERY;
		
		try{
			String id = UUID.randomUUID().toString();
			String type = request.get(Constants.TYPE);
			String createdDate = request.get(Constants.CREATED_DATE);
			String userId = request.get(Constants.USER_ID);
			String description = request.get(Constants.DESCRIPTION);
			
			Object[] params = {id, type, createdDate, userId, description};
			commonDAO.update(sqlQuery, params);
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.ISSUE_REPORTED_SUCCESSFULLY);
			
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}

}

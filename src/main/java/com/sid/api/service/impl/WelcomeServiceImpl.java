package com.sid.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sid.api.services.WelcomeService;

//@Service makes it singleton.
@Service
public class WelcomeServiceImpl implements WelcomeService {

	/*@Autowired
	CommonDAO commonDAO;*/

	public List<Map<String, Object>> welcomeMessage() {

		Map<String, Object> response = new HashMap<String, Object>();

		response.put("result", "Welcome to Beezy Web APIs. You are successfully connected!");

		/*List<Map<String, Object>> data = commonDAO.fetch("select * from beezy_user_type;");
		
		data.add(response);*/
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		data.add(response);
		return data;
	}

}

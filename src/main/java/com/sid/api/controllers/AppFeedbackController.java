package com.sid.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.api.services.AppFeedbackService;

@CrossOrigin
@RequestMapping("/feedback")
@RestController
public class AppFeedbackController {
	
	@Autowired
	private AppFeedbackService appFeedbackService;
	
	@PostMapping("/addAppFeedback")
	public Map<String, String> addAppFeedback(@RequestBody Map<String, String> request){
		return appFeedbackService.addAppFeedback(request);
	}
	
	@GetMapping("/fetchAppFeedback")
	public List<Map<String, Object>> fetchAppFeedback(){
		return appFeedbackService.fetchAppFeedback();
	}

}

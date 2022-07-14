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

import com.sid.api.services.QuestionService;

@CrossOrigin
@RequestMapping("/question")
@RestController
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@PostMapping("/addQuestions")
	public Map<String, String> addQuestions(@RequestBody List<Map<String, String>> request){
		
		return questionService.addQuestions(request);
	}
	
	@PostMapping("/deleteQuestionById")
	public Map<String, String> deleteQuestionById(@RequestBody Map<String, String> request){
		
		return questionService.deleteQuestionById(request);
	}
	
	@PostMapping("/updateQuestionById")
	public Map<String, String> updateQuestionById(@RequestBody Map<String, String> request){
		
		return questionService.updateQuestionById(request);
	}
	
	@GetMapping("/fetchAllQuestions")
	public List<Map<String, Object>> fetchAllQuestions(){
		return questionService.fetchAllQuestions();
	}
	
	@PostMapping("/deleteAllQuestions")
	public Map<String, String> deleteAllQuestions(){
		return questionService.deleteAllQuestions();
	}

}

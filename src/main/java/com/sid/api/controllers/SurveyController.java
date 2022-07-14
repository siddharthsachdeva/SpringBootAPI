package com.sid.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.api.services.SurveyService;

@CrossOrigin
@RequestMapping("/survey")
@RestController
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	@Transactional
	@PostMapping("/addRatingsAndReviews")
	public Map<String, String> addRatingsAndReviews(@RequestBody List<Map<String, Object>> request){
		return surveyService.addRatingsAndReviewsService(request);
		
	}
	
	@GetMapping("/searchReviewsByFilter/{companyName}/{suburb}/{rating}/{review}")
	public List<Map<String, Object>> searchReviewsByFilter(@PathVariable String companyName, @PathVariable String suburb, @PathVariable String rating, @PathVariable String review){	
		return surveyService.searchReviewsByFilter(companyName, suburb, rating, review);
	}
	
	@GetMapping("/fetchAllUsersReviewsDetails")
	public List<Map<String, Object>> fetchAllUsersReviewsDetails(){	
		return surveyService.fetchAllUsersReviewsDetails();
	}
	
	@PostMapping("/removeReviews")
	public Map<String, Object> removeCategories(@RequestBody List<Map<String, String>> request){
		return surveyService.removeReviews(request);
	}
	
	@GetMapping("/fetchRecentReviewedCompaniesByUserId/{userId}")
	public List<Map<String, Object>> fetchRecentReviewedCompaniesByUserId(@PathVariable String userId){	
		return surveyService.fetchRecentReviewedCompaniesByUserId(userId);
	}
	
	@GetMapping("/fetchAllAustralianSuburbs/")
	public List<Map<String, Object>> fetchAllAustralianSuburbs(){	
		return surveyService.fetchAllAustralianSuburbs();
	}

}

package com.sid.api.services;

import java.util.List;
import java.util.Map;

public interface SurveyService {

	Map<String, String> addRatingsAndReviewsService(List<Map<String, Object>> request);
	List<Map<String, Object>> searchReviewsByFilter(String companyName, String suburb, String rating, String review);
	List<Map<String, Object>> fetchAllUsersReviewsDetails();
	Map<String, Object> removeReviews(List<Map<String, String>> request);
	List<Map<String, Object>> fetchRecentReviewedCompaniesByUserId(String userId);
	List<Map<String, Object>> fetchAllAustralianSuburbs();
	
}

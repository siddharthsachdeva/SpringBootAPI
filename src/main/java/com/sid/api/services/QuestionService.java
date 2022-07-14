package com.sid.api.services;

import java.util.List;
import java.util.Map;

public interface QuestionService {

	Map<String, String> addQuestions(List<Map<String, String>> request);
	List<Map<String, Object>> fetchAllQuestions();
	Map<String, String> deleteQuestionById(Map<String, String> request);
	Map<String, String> updateQuestionById(Map<String, String> request);
	Map<String, String> deleteAllQuestions();

}

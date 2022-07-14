package com.sid.api.services;

import java.util.List;
import java.util.Map;

public interface AppFeedbackService {

	Map<String, String> addAppFeedback(Map<String, String> request);

	List<Map<String, Object>> fetchAppFeedback();

}

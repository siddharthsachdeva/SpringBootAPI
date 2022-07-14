package com.sid.api.services;

import java.util.List;
import java.util.Map;

public interface InfluenceService {

	Map<String, String> addInfluences(List<Map<String, String>> requestMap);

	Map<String, String> removeInfluences(List<Map<String, String>> request);

	Map<String, String> updateInfluence(Map<String, String> request);

	List<Map<String, Object>> fetchAllInfluences();

}

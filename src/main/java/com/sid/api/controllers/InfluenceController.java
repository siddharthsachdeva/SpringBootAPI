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

import com.sid.api.services.InfluenceService;

@CrossOrigin
@RequestMapping("/influence")
@RestController
public class InfluenceController {
	
	@Autowired
	private InfluenceService influenceService;
	
	@GetMapping("/fetchAllInfluences")
	public List<Map<String, Object>> fetchAllInfluences(){
		return influenceService.fetchAllInfluences();
	}
	
	@PostMapping("/addInfluences")
	public Map<String, String> addInfluences(@RequestBody List<Map<String, String>> request){
		return influenceService.addInfluences(request);
	}
	
	@PostMapping("/removeInfluences")
	public Map<String, String> removeInfluences(@RequestBody List<Map<String, String>> request){
		return influenceService.removeInfluences(request);
	}
	
	@PostMapping("/updateInfluence")
	public Map<String, String> updateInfluence(@RequestBody Map<String, String> request){
		return influenceService.updateInfluence(request);
	}

}

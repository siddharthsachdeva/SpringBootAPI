package com.sid.api.controllers;

import java.util.List;
import java.util.Map;

import com.sid.api.service.impl.WelcomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class WelcomeController {

	@Autowired
	private WelcomeServiceImpl welcomeService;
	
	@GetMapping("/")
	public List<Map<String, Object>> welcome(){

		return welcomeService.welcomeMessage();
	}
	
}

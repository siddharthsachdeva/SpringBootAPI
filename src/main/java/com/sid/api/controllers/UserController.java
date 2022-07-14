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

import com.sid.api.services.UserService;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/registerUser")
	public Map<String, String> registerUser(@RequestBody Map<String, String> requestMap ){
		return userService.registerUserService(requestMap);
	}
	
	@PostMapping("/deactivateUser")
	public Map<String, String> deactivateUser(@RequestBody Map<String, String> requestMap ){
		return userService.deactivateUser(requestMap);
	}
	
	/*@PostMapping("/reactivateUser")
	public Map<String, String> reactivateUser(@RequestBody Map<String, String> requestMap ){
		return userService.reactivateUser(requestMap);
	}*/
	
	@PostMapping("/loginUser")
	public List<Map<String, Object>> loginUser(@RequestBody Map<String, String> requestMap ){
		return userService.loginUser(requestMap);
	}
	
	@PostMapping("/toggleAccountStatus")
	public Map<String, String> toggleAccountStatus(@RequestBody Map<String, String> request ){
		return userService.toggleAccountStatus(request);
	}
	
	@PostMapping("/toggleOptOutStatus")
	public Map<String, String> toggleOptOutStatus(@RequestBody Map<String, String> request ){
		return userService.toggleOptOutStatus(request);
	}
	
	@GetMapping("/fetchAllUsers")
	public List<Map<String, Object>> fetchAllUsers(){
		return userService.fetchAllUsers();
	}
	
	@PostMapping("/updateUserCountry")
	public Map<String, String> updateUserCountry(@RequestBody Map<String, String> request ){
		return userService.updateUserCountry(request);
	}
	
	@GetMapping("fetchAllCountries")
	public String fetchAllCountries(){
		return userService.fetchAllCountries();
	} 

}

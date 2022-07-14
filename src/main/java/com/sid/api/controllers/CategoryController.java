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

import com.sid.api.services.CategoryService;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/addCategory")
	public Map<String, String> addCategory(@RequestBody Map<String, Object> request){
		return categoryService.addCategory(request);
	}
	
	//Not charged for this, added this one for uploading data in DB.
	@PostMapping("/addCategories")
	public Map<String, String> addCategories(@RequestBody List<Map<String, Object>> request){
		return categoryService.addCategories(request);
	}
	
	@GetMapping("/fetchAllCategories")
	public List<Map<String, Object>> fetchAllCategories(){
		return categoryService.fetchAllCategories();
	}
	
	@PostMapping("/removeCategories")
	public Map<String, Object> removeCategories(@RequestBody List<Map<String, String>> request){
		return categoryService.removeCategories(request);
	}
	
	@PostMapping("/updateCategory")
	public Map<String, String> updateCategory(@RequestBody Map<String, Object> request){
		return categoryService.updateCategory(request);
	}

}

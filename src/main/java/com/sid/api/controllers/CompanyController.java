package com.sid.api.controllers;

import java.util.List;
import java.util.Map;

import com.sid.api.pojo.PlacesApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.api.services.CompanyService;

@CrossOrigin
@RequestMapping("/company")
@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@PostMapping("/fetchInfluencesByCompanyIdOrCompanyName")
	public List<Map<String, Object>> fetchInfluencesByCompanyIdOrCompanyNameService(@RequestBody Map<String, String> requestMap){	
		return companyService.fetchInfuencesByCompanyNameOrId(requestMap);
	}
	
	@GetMapping("/fetchInfluencesByCompanyId/{companyId}")
	public List<Map<String, Object>> fetchInfluencesByCompanyId(@PathVariable String companyId){
		
		return companyService.fetchInfluencesByCompanyId(companyId);
	}
	
	@GetMapping("/searchCompanyByKeywordAndLocation/{keyword}/{latitude}/{longitude}")
	public PlacesApiResponse searchCompanyByKeywordAndLocation(@PathVariable String keyword, @PathVariable String latitude, @PathVariable String longitude){

		return companyService.searchCompanyByKeywordAndLocationService(keyword, latitude, longitude);
	}
	
	@GetMapping("/fetchAllCompanies")
	public List<Map<String, Object>> fetchAllCompanies(){	

		return companyService.fetchAllCompanies();
	}
	
	@GetMapping("/nearbySearch/{latitude}/{longitude}")
	public PlacesApiResponse nearbySearch(@PathVariable String latitude, @PathVariable String longitude){	
		return companyService.nearbySearch(latitude, longitude);
	}	
	
	@PostMapping("/addCompany")
	public Map<String, String> addCompany(@RequestBody Map<String, Object> requestMap){
		return companyService.addCompany(requestMap);
	}

	//Not charged for this, added this one for uploading data in DB.
	@PostMapping("/addCompanies")
	public Map<String, String> addCompanies(@RequestBody List<Map<String, Object>> requestBody){
		return companyService.addCompanies(requestBody);
	}
	
	@PostMapping("/removeCompany")
	public Map<String, String> removeCompany(@RequestBody Map<String, String> requestMap){
		return companyService.removeCompany(requestMap);
	}
	
	@PostMapping("/updateCompanyDetails")
	public Map<String, String> updateCompanyDetails(@RequestBody Map<String, Object> requestMap){
		return companyService.updateCompanyDetails(requestMap);
	}

}

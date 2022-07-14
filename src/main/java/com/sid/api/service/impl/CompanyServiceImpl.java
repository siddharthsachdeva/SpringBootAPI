package com.sid.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sid.api.utility.Constants;
import com.sid.api.utility.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.api.client.NearByAPIClient;
import com.sid.api.dao.CommonDAO;
import com.sid.api.pojo.PlacesApiResponse;
import com.sid.api.services.CompanyService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CommonDAO commonDAO;
	
	
	@Override
	public List<Map<String, Object>> fetchInfuencesByCompanyNameOrId(Map<String, String> requestMap) {
		String companyId = requestMap.get(Constants.COMPANY_ID);
		String companyName = requestMap.get(Constants.COMPANY_NAME);
		String sqlQuery = SQLQueries.FETCH_INFLUENCES_BY_COMPANY_NAME_OR_COMPANY_ID;

		if (companyId == null) {
			companyId = "";
		} else if (companyName == null) {
			companyName = "";
		}
		Object[] params = { companyId, companyName };
		List<Map<String, Object>> data = commonDAO.fetch(sqlQuery, params);
		return data;
	}


	@Override
	public List<Map<String, Object>> fetchInfluencesByCompanyId(String companyId) {
		
		String sqlQuery = SQLQueries.FETCH_INFLUENCES_BY_COMPANY_ID;

		if (companyId == null) {
			companyId = "";
		} 
		
		Object[] params = { companyId };
		List<Map<String, Object>> data = commonDAO.fetch(sqlQuery, params);
		return data;
	}

	@Override
	public PlacesApiResponse searchCompanyByKeywordAndLocationService(String latitude, String longitude,
			String keyword) {

		if (keyword == null) {
			keyword = "";
		}

		NearByAPIClient apiClient = new NearByAPIClient();
		String result = apiClient.getPlacesApiResult(latitude, longitude, keyword);
		ObjectMapper mapper = new ObjectMapper();

		PlacesApiResponse placesApiResponse = null;
		try {
			System.out.println(result);
			placesApiResponse = mapper.readValue(result, PlacesApiResponse.class);

		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return placesApiResponse;
	}

	@Override
	public PlacesApiResponse nearbySearch(String latitude, String longitude) {

		System.out.println("Service Latitude: " + latitude + "\n Service Longitude: " + longitude);

		NearByAPIClient apiClient = new NearByAPIClient();
		String result = apiClient.getPlacesApiResult(latitude, longitude);
		ObjectMapper mapper = new ObjectMapper();

		PlacesApiResponse placesApiResponse = null;
		try {
			System.out.println(result);
			placesApiResponse = mapper.readValue(result, PlacesApiResponse.class);

		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return placesApiResponse;
	}

	@Override
	public void addCompanyDetails(String companyId, String companyName, String companyDescription, String website,
			String email, String country, String state, String city, String street, String zipcode, String fbLink,
			String twitterLink, String logo, String vincity, String phoneNo, String additionalPhoneNo,
			List<String> categories, String gender, String targetedGender, String ageRange, List<String> industryTagIds) throws Exception {

		String sqlQuery = SQLQueries.INSERT_INTO_APP_COMPANY_DETAILS_QUERY;
		Object[] params = { companyId, companyName, companyDescription, website, email, country, state, city, street,
				zipcode, fbLink, twitterLink, logo, vincity, phoneNo, additionalPhoneNo, gender, targetedGender, ageRange };
		commonDAO.update(sqlQuery, params);
		
		String sqlQueryAssignCategoriesToCompany = SQLQueries.ASSIGN_CATEGORIES_TO_COMPANY_QUERY;
		
		for(String categoryId : categories){
			Object[] paramsForCompanyCategories = {UUID.randomUUID().toString(), companyId, categoryId};
			commonDAO.update(sqlQueryAssignCategoriesToCompany, paramsForCompanyCategories);
		}
		
		String sqlQueryAssignIndustryTagsToCompany = SQLQueries.ASSIGN_INDUSTRY_TAGS_TO_COMPANY_QUERY;
		
		for(String industryTagId : industryTagIds){
			String id = UUID.randomUUID().toString();
			Object[] paramsForCompanyIndustryTags = {id, companyId, industryTagId};
			commonDAO.update(sqlQueryAssignIndustryTagsToCompany, paramsForCompanyIndustryTags);
		}
	}
	
	@Transactional
	@Override
	public Map<String, String> addCompany(Map<String, Object> requestMap) {
		Map<String, String> response = new HashMap<>();

		try {
			String companyId = UUID.randomUUID().toString();
			String companyName = requestMap.get(Constants.COMPANY_NAME).toString() == null ? "" :  requestMap.get(Constants.COMPANY_NAME).toString();
			String companyDescription = requestMap.get(Constants.COMPANY_DESCRIPTION) == null ? "" :  requestMap.get(Constants.COMPANY_DESCRIPTION).toString();
			String website = requestMap.get(Constants.WEBSITE) == null ? "" : requestMap.get(Constants.WEBSITE).toString();
			String email = requestMap.get(Constants.EMAIL) == null ? "" : requestMap.get(Constants.EMAIL).toString();;
			String country = requestMap.get(Constants.COUNTRY) == null ? "" :  requestMap.get(Constants.COUNTRY).toString();
			String state = requestMap.get(Constants.STATE) == null ? "" : requestMap.get(Constants.STATE).toString();
			String city = requestMap.get(Constants.CITY) == null ? "" :  requestMap.get(Constants.CITY).toString();
			String street = requestMap.get(Constants.STREET) == null ? "" : requestMap.get(Constants.STREET).toString();
			String zipcode = requestMap.get(Constants.ZIPCODE) == null ? "" :  requestMap.get(Constants.ZIPCODE).toString();
			String logo = requestMap.get(Constants.LOGO) == null ? "" :  requestMap.get(Constants.LOGO).toString();
			String vicinity = requestMap.get(Constants.VICINITY) == null ? "" : requestMap.get(Constants.VICINITY).toString();
			String fbLink = requestMap.get(Constants.FB_LINK) == null ? "" :  requestMap.get(Constants.FB_LINK).toString();
			String twitterLink = requestMap.get(Constants.TWITTER_LINK) == null ? "" : requestMap.get(Constants.TWITTER_LINK).toString();
			String phoneNo = requestMap.get(Constants.PHONE_NO) == null ? "" : requestMap.get(Constants.PHONE_NO).toString();
			String additionalPhoneNo = requestMap.get(Constants.ADDITIONAL_PHONE_NO) == null ? "" :  requestMap.get(Constants.ADDITIONAL_PHONE_NO).toString();
			String gender = requestMap.get(Constants.GENDER) == null ? "" : requestMap.get(Constants.GENDER).toString();
			String targetedGender = requestMap.get(Constants.TARGETED_GENDER) == null ? "" : requestMap.get(Constants.TARGETED_GENDER).toString();
			String ageRange = requestMap.get(Constants.AGE_RANGE) == null ? "" : requestMap.get(Constants.AGE_RANGE).toString();
			List<String> industryTagIds = new ArrayList<>();
			@SuppressWarnings("unchecked")
			List<String> industryTags = (List<String>) requestMap.get(Constants.INDUSTRY_TAGS) == null ? new ArrayList<>() :  (List<String>) requestMap.get(Constants.INDUSTRY_TAGS);
			String sqlQueryValidateIndustryTag = SQLQueries.VALIDATE_INDUSTRY_TAG_QUERY;
			String sqlQueryInsertIndustryTag = SQLQueries.ADD_INDUSTRY_TAG_QUERY;
			String sqlQueryFetchIndustryTagIdByName = SQLQueries.FETCH_INDUSTRY_TAG_ID_BY_INDUSTRY_TAG_QUERY;
			
			if(industryTags.size() > 0){
				for(String industryTag : industryTags){
					Object[] params = {industryTag};
					if(!commonDAO.validate(sqlQueryValidateIndustryTag, params)){
						String industryTagId = UUID.randomUUID().toString();
						Object[] paramsAddIndustryTag = {industryTagId, industryTag};
						commonDAO.update(sqlQueryInsertIndustryTag, paramsAddIndustryTag);
						industryTagIds.add(industryTagId);
					}else{
						Object[] paramsFetchIndustryTagId = {industryTag};
						String industryTagId = commonDAO.fetchString(sqlQueryFetchIndustryTagIdByName, paramsFetchIndustryTagId);
						industryTagIds.add(industryTagId);
					}
				}
				
				
			}
			
			@SuppressWarnings("unchecked")
			List<String> categories = (List<String>) requestMap.get(Constants.COMPANY_CATEGORIES) == null ? new ArrayList<>() :  (List<String>) requestMap.get(Constants.COMPANY_CATEGORIES);

			addCompanyDetails(companyId, companyName, companyDescription, website, email, country, state, city, street,
					zipcode, fbLink, twitterLink, logo, vicinity, phoneNo, additionalPhoneNo, categories, gender, targetedGender, ageRange, industryTagIds);

			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.COMPANY_ADDED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			e.printStackTrace();
		}
		return response;
	}

	@Transactional
	@Override
	public Map<String, String> removeCompany(Map<String, String> requestMap) {
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.REMOVE_COMPANY_BY_ID_QUERY;
		String sqlQueryDeleteCompanyCategories = SQLQueries.DELETE_COMPANY_CATEGORIES_QUERY;
		String sqlQueryDeleteCompanyIndustryTags = SQLQueries.DELETE_COMPANY_INDUSTRY_TAGS_QUERY;
		try {
			String companyId = requestMap.get(Constants.COMPANY_ID);			
			Object[] params = { companyId };
			
			commonDAO.update(sqlQuery, params);
			commonDAO.update(sqlQueryDeleteCompanyCategories, params);
			commonDAO.update(sqlQueryDeleteCompanyIndustryTags, params);
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.COMPANY_REMOVED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}
	@Transactional
	@Override
	public Map<String, String> updateCompanyDetails(Map<String, Object> requestMap) {
		
		System.out.println("Inside update company details webservice");
		
		Map<String, String> response = new HashMap<>();
		String sqlQuery = SQLQueries.UPDATE_COMPANY_BY_ID_QUERY;
		try {
			String companyId = requestMap.get(Constants.COMPANY_ID).toString();
			String companyName = requestMap.get(Constants.COMPANY_NAME) == null ? "" : requestMap.get(Constants.COMPANY_NAME).toString();
			String companyDescription = requestMap.get(Constants.COMPANY_DESCRIPTION) == null ? "" : requestMap.get(Constants.COMPANY_DESCRIPTION).toString();
			String website = requestMap.get(Constants.WEBSITE) == null ? "" : requestMap.get(Constants.WEBSITE).toString();
			String email = requestMap.get(Constants.EMAIL) == null ? "" : requestMap.get(Constants.EMAIL).toString();
			String country = requestMap.get(Constants.COUNTRY) == null ? "" : requestMap.get(Constants.COUNTRY).toString();
			String state = requestMap.get(Constants.STATE) == null ? "" : requestMap.get(Constants.STATE).toString();
			String city = requestMap.get(Constants.CITY) == null ? "" : requestMap.get(Constants.CITY).toString();
			String street = requestMap.get(Constants.STREET) == null ? "" : requestMap.get(Constants.STREET).toString();
			String zipcode = requestMap.get(Constants.ZIPCODE) == null ? "" : requestMap.get(Constants.ZIPCODE).toString();
			String fbLink = requestMap.get(Constants.FB_LINK) == null ? "" : requestMap.get(Constants.FB_LINK).toString();
			String twitterLink = requestMap.get(Constants.TWITTER_LINK) == null ? "" : requestMap.get(Constants.TWITTER_LINK).toString();
			@SuppressWarnings("unchecked")
			List<String> categories = (List<String>) requestMap.get(Constants.COMPANY_CATEGORIES) == null ? new ArrayList<>() : (List<String>) requestMap.get(Constants.COMPANY_CATEGORIES);
			String logo = requestMap.get(Constants.LOGO) == null ? "" : requestMap.get(Constants.LOGO).toString();
			String vicinity = requestMap.get(Constants.VICINITY) == null ? "" : requestMap.get(Constants.VICINITY).toString();
			String phoneNo = requestMap.get(Constants.PHONE_NO) == null ? "" : requestMap.get(Constants.PHONE_NO).toString();
			String additionalPhoneNo = requestMap.get(Constants.ADDITIONAL_PHONE_NO) == null ? "" : requestMap.get(Constants.ADDITIONAL_PHONE_NO).toString();
			
			Object[] params = { companyName, companyDescription, website, email, country, state, city, street,
					zipcode, fbLink, twitterLink, logo, vicinity, phoneNo, additionalPhoneNo, companyId};
			
			commonDAO.update(sqlQuery, params);
			
			
			String deleteCompanyCategoriesQuery = SQLQueries.DELETE_COMPANY_CATEGORIES_QUERY;
			Object[] paramsForDelete = {companyId};
			
			commonDAO.update(deleteCompanyCategoriesQuery, paramsForDelete);
			
			String sqlQueryAssignCategoriesToCompany = SQLQueries.ASSIGN_CATEGORIES_TO_COMPANY_QUERY;
			
			for(String catId : categories){
				Object[] paramsForCompanyCategories = {UUID.randomUUID().toString(), companyId, catId};
				commonDAO.update(sqlQueryAssignCategoriesToCompany, paramsForCompanyCategories);
			}
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.COMPANY_UPDATED_SUCCESSFULLY);

		} catch (Exception e) {
			e.printStackTrace();
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> fetchAllCompanies() {
		List<Map<String, Object>> data = new ArrayList<>();
		List<Map<String, Object>> response = new ArrayList<>();
		String sqlQuery = SQLQueries.FETCH_ALL_COMPANIES_QUERY;
		String sqlQueryCompanyCategories = SQLQueries.FETCH_COMPANY_CATEGORIES_QUERY;
		String sqlQueryCompanyCategoryIds = SQLQueries.FETCH_COMPANY_CATEGORY_IDS_QUERY;
		
		try{
			
			data = commonDAO.fetch(sqlQuery);
			
			for(Map<String, Object> company : data ){
				String id = company.get(Constants.ID).toString();
				Object[] params = {id};
				List<String> categories = commonDAO.fetchDataList(sqlQueryCompanyCategories, params);
				List<String> categoryIds = commonDAO.fetchDataList(sqlQueryCompanyCategoryIds, params);
				company.put(Constants.COMPANY_CATEGORIES, categories);
				company.put(Constants.CATEGORY_IDS, categoryIds);
				response.add(company);
			}
			
		}catch(Exception e){
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			
			response.add(errorResponse);
		}
		return response;
	}

	@Transactional
	@Override
	public Map<String, String> addCompanies(List<Map<String, Object>> requestBody) {
		Map<String, String> response = new HashMap<>();

		try {
			
			for(Map<String, Object> requestMap : requestBody){
			
			String companyId = UUID.randomUUID().toString();
			String companyName = requestMap.get(Constants.COMPANY_NAME).toString() == null ? "" :  requestMap.get(Constants.COMPANY_NAME).toString();
			String companyDescription = requestMap.get(Constants.COMPANY_DESCRIPTION) == null ? "" :  requestMap.get(Constants.COMPANY_DESCRIPTION).toString();
			String website = requestMap.get(Constants.WEBSITE) == null ? "" : requestMap.get(Constants.WEBSITE).toString();
			String email = requestMap.get(Constants.EMAIL) == null ? "" : requestMap.get(Constants.EMAIL).toString();;
			String country = requestMap.get(Constants.COUNTRY) == null ? "" :  requestMap.get(Constants.COUNTRY).toString();
			String state = requestMap.get(Constants.STATE) == null ? "" : requestMap.get(Constants.STATE).toString();
			String city = requestMap.get(Constants.CITY) == null ? "" :  requestMap.get(Constants.CITY).toString();
			String street = requestMap.get(Constants.STREET) == null ? "" : requestMap.get(Constants.STREET).toString();
			String zipcode = requestMap.get(Constants.ZIPCODE) == null ? "" :  requestMap.get(Constants.ZIPCODE).toString();
			String logo = requestMap.get(Constants.LOGO) == null ? "" :  requestMap.get(Constants.LOGO).toString();
			String vicinity = requestMap.get(Constants.VICINITY) == null ? "" : requestMap.get(Constants.VICINITY).toString();
			String fbLink = requestMap.get(Constants.FB_LINK) == null ? "" :  requestMap.get(Constants.FB_LINK).toString();
			String twitterLink = requestMap.get(Constants.TWITTER_LINK) == null ? "" : requestMap.get(Constants.TWITTER_LINK).toString();
			String phoneNo = requestMap.get(Constants.PHONE_NO) == null ? "" : requestMap.get(Constants.PHONE_NO).toString();
			String additionalPhoneNo = requestMap.get(Constants.ADDITIONAL_PHONE_NO) == null ? "" :  requestMap.get(Constants.ADDITIONAL_PHONE_NO).toString();
			String gender = requestMap.get(Constants.GENDER) == null ? "" : requestMap.get(Constants.GENDER).toString();
			String targetedGender = requestMap.get(Constants.TARGETED_GENDER) == null ? "" : requestMap.get(Constants.TARGETED_GENDER).toString();
			String ageRange = requestMap.get(Constants.AGE_RANGE) == null ? "" : requestMap.get(Constants.AGE_RANGE).toString();
			
			@SuppressWarnings("unchecked")
			List<String> categories = (List<String>) requestMap.get(Constants.COMPANY_CATEGORIES) == null ? new ArrayList<>() :  (List<String>) requestMap.get(Constants.COMPANY_CATEGORIES);
			List<String> industryTagIds = new ArrayList<>();
			@SuppressWarnings("unchecked")
			List<String> industryTags = (List<String>) requestMap.get(Constants.INDUSTRY_TAGS) == null ? new ArrayList<>() :  (List<String>) requestMap.get(Constants.INDUSTRY_TAGS);
			String sqlQueryValidateIndustryTag = SQLQueries.VALIDATE_INDUSTRY_TAG_QUERY;
			String sqlQueryInsertIndustryTag = SQLQueries.ADD_INDUSTRY_TAG_QUERY;
			String sqlQueryFetchIndustryTagIdByName = SQLQueries.FETCH_INDUSTRY_TAG_ID_BY_INDUSTRY_TAG_QUERY;
			System.out.println("just outside industry tags check");
			if(industryTags.size() > 0){
				System.out.println("inside industry tags check");
				for(String industryTag : industryTags){
					Object[] params = {industryTag};
					if(!commonDAO.validate(sqlQueryValidateIndustryTag, params)){
						String industryTagId = UUID.randomUUID().toString();
						Object[] paramsAddIndustryTag = {industryTagId, industryTag};
						commonDAO.update(sqlQueryInsertIndustryTag, paramsAddIndustryTag);
						industryTagIds.add(industryTagId);
					}else{
						Object[] paramsFetchIndustryTagId = {industryTag};
						String industryTagId = commonDAO.fetchString(sqlQueryFetchIndustryTagIdByName, paramsFetchIndustryTagId);
						industryTagIds.add(industryTagId);
					}
				}
				
				
			}
			
			addCompanyDetails(companyId, companyName, companyDescription, website, email, country, state, city, street,
					zipcode, fbLink, twitterLink, logo, vicinity, phoneNo, additionalPhoneNo, categories, gender, targetedGender, ageRange, industryTagIds);
			
			}

			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.COMPANY_ADDED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			e.printStackTrace();
		}
		return response;

	}

	

}

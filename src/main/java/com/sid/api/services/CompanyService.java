package com.sid.api.services;

import java.util.List;
import java.util.Map;

import com.sid.api.pojo.PlacesApiResponse;

public interface CompanyService {

	List<Map<String, Object>> fetchInfluencesByCompanyId(String companyId);
	
	PlacesApiResponse searchCompanyByKeywordAndLocationService(String latitude, String longitude, String keyword);

	PlacesApiResponse nearbySearch(String latitude, String longitude);

	Map<String, String> addCompany(Map<String, Object> requestMap);

	Map<String, String> removeCompany(Map<String, String> requestMap);

	List<Map<String, Object>> fetchAllCompanies();

/*	void addCompanyDetails(String companyId, String companyName, String companyDescription, String website,
			String email, String country, String state, String city, String street, String zipcode, String fbLink,
			String twitterLink, String logo, String vincity, String phoneNo,
			String additionalPhoneNo, List<String> categories) throws Exception;
*/
	Map<String, String> updateCompanyDetails(Map<String, Object> requestMap);

	Map<String, String> addCompanies(List<Map<String, Object>> requestBody);

	void addCompanyDetails(String companyId, String companyName, String companyDescription, String website,
			String email, String country, String state, String city, String street, String zipcode, String fbLink,
			String twitterLink, String logo, String vincity, String phoneNo, String additionalPhoneNo,
			List<String> categories, String gender, String targetedGender, String ageRange, List<String> industryTagIds)
			throws Exception;

	List<Map<String, Object>> fetchInfuencesByCompanyNameOrId(Map<String, String> requestMap);	

}

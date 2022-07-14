package com.sid.api.service.impl;

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

import com.sid.api.dao.CommonDAO;
import com.sid.api.services.CompanyService;
import com.sid.api.services.SurveyService;

@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private CompanyService companyService;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Map<String, String> addRatingsAndReviewsService(List<Map<String, Object>> request) {
		
		Map<String, String> response = new HashMap<>();
		String rating, description, createdOn, userId = null, companyId = null, 
				isReviewedFromNearby;
		List<String> influenceIds = new ArrayList<>();
		try {
			for (Map<String, Object> data : request) {

				rating = data.get(Constants.RATING).toString();
				description = data.get(Constants.REVIEW).toString();
				createdOn = data.get(Constants.CREATED_ON).toString();
				userId = data.get(Constants.USER_ID).toString();
				
				influenceIds = (List<String>) data.get(Constants.INFLUENCE_IDS) == null ? new ArrayList<>() :  (List<String>) data.get(Constants.INFLUENCE_IDS);
				isReviewedFromNearby = data.get(Constants.IS_REVIEWED_FROM_NEARBY).toString();

				if (isReviewedFromNearby != null && isReviewedFromNearby.equalsIgnoreCase(Constants.YES)) {
					companyId = UUID.randomUUID().toString();
					System.out.println("inside if company id:"+companyId);
					String companyName = data.get(Constants.COMPANY_NAME).toString();
					String companyDescription = data.get(Constants.COMPANY_DESCRIPTION) == null ? "" : data.get(Constants.COMPANY_DESCRIPTION).toString();
					String website = data.get(Constants.WEBSITE) == null ? "" : data.get(Constants.WEBSITE).toString();
					String email = data.get(Constants.EMAIL) == null ? "" : data.get(Constants.EMAIL).toString();
					String country = data.get(Constants.COUNTRY) == null ? "" : data.get(Constants.COUNTRY).toString();
					String state = data.get(Constants.STATE) == null ? "" : data.get(Constants.STATE).toString();
					String city = data.get(Constants.CITY) == null ? "" : data.get(Constants.CITY).toString();
					String street = data.get(Constants.STREET) == null ? "" : data.get(Constants.STREET).toString();
					String zipcode = data.get(Constants.ZIPCODE) == null ? "" : data.get(Constants.ZIPCODE).toString();
					String fbLink = data.get(Constants.FB_LINK) == null ? "" : data.get(Constants.FB_LINK).toString();
					String twitterLink = data.get(Constants.TWITTER_LINK) == null ? "" : data.get(Constants.TWITTER_LINK).toString();
					String logo = data.get(Constants.LOGO) == null ? "" : data.get(Constants.LOGO).toString();
					String vicinity = data.get(Constants.VICINITY) == null ? "" : data.get(Constants.VICINITY).toString();
					String phoneNo = data.get(Constants.PHONE_NO) == null ? "" : data.get(Constants.PHONE_NO).toString();
					String additionalPhoneNo = data.get(Constants.ADDITIONAL_PHONE_NO) == null ? "" : data.get(Constants.ADDITIONAL_PHONE_NO).toString();
					String gender = data.get(Constants.GENDER) == null ? "" : data.get(Constants.GENDER).toString();
					String targetedGender = data.get(Constants.TARGETED_GENDER) == null ? "" : data.get(Constants.TARGETED_GENDER).toString();
					String ageRange = data.get(Constants.AGE_RANGE) == null ? "" : data.get(Constants.AGE_RANGE).toString();
					
					@SuppressWarnings("unchecked")
					List<String> categories = (List<String>) data.get(Constants.COMPANY_CATEGORIES) == null ? new ArrayList<>() :  (List<String>) data.get(Constants.COMPANY_CATEGORIES);
					
					List<String> industryTagIds = new ArrayList<>();
					@SuppressWarnings("unchecked")
					List<String> industryTags = (List<String>) data.get(Constants.INDUSTRY_TAGS) == null ? new ArrayList<>() :  (List<String>) data.get(Constants.INDUSTRY_TAGS);
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
					companyService.addCompanyDetails(companyId, companyName, companyDescription, website, email, country, state, city, street,
							zipcode, fbLink, twitterLink, logo, vicinity, phoneNo, additionalPhoneNo, categories, gender, targetedGender, ageRange, industryTagIds);
				}else{
					companyId = data.get(Constants.COMPANY_ID).toString();
				}

				if (rating == null) {
					rating = "";
				} else if (description == null) {
					description = "";
				} else if (createdOn == null) {
					createdOn = "";
				} else if ((userId == null || userId.isEmpty())	|| influenceIds == null || influenceIds.isEmpty()) {
					response.put(Constants.STATUS, Constants.ERROR);
					response.put(Constants.MESSAGE, Constants.INVALID_REQUEST);
					return response;
				}

				String location = data.get(Constants.LOCATION) == null ? "" : data.get(Constants.LOCATION).toString();
				String surveyAnswersId = UUID.randomUUID().toString();
				System.out.println("before inserting into beezy survey answers company id:"+companyId);
				String sqlQueryInsertIntoBeezySurveyAnswers = SQLQueries.INSERT_INTO_APP_SURVEY_ANSWERS_QUERY;
				Object[] paramsForBeezySurveyAnswers = { surveyAnswersId, rating, description, createdOn, userId,
						companyId, location };

				commonDAO.update(sqlQueryInsertIntoBeezySurveyAnswers, paramsForBeezySurveyAnswers);

				String sqlQueryInsertIntoBeezySurveyAnswersInfluences = SQLQueries.INSERT_INTO_APP_SURVEY_ANSWERS_INFLUENCES_QUERY;
				
				for(String influenceId : influenceIds){
					Object[] paramsForBeezySurveyAnswersInfluences = { UUID.randomUUID().toString(), surveyAnswersId,
							influenceId };
					commonDAO.update(sqlQueryInsertIntoBeezySurveyAnswersInfluences, paramsForBeezySurveyAnswersInfluences);					
				}
				
				//Check if user completed enough reviews to get more rewards, if yes then insert the new reward into beezy_user_rewards table.

				List<String> rewardIdsShouldAvailableForUser = getRewardIdsAvailableForUser(userId); 
				List<String> currentUserAvailableRewards = getCurrentUserRewardIds(userId);			
				List<String> newRewardIdsForUser = getNewRewards(rewardIdsShouldAvailableForUser, currentUserAvailableRewards);
				
				addNewRewardsForUser(userId, newRewardIdsForUser);
				
				response.put(Constants.STATUS, Constants.OK);
				response.put(Constants.MESSAGE, Constants.RATINGS_AND_REVIEWS_SUCCESS);

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}

		return response;
	}

	private void addNewRewardsForUser(String userId, List<String> newRewardIdsForUser) throws Exception {
		String sqlQuery = SQLQueries.INSERT_INTO_APP_USER_REWARD_QUERY;
		for(String rewardId : newRewardIdsForUser){
			Object[] params = {UUID.randomUUID().toString(), userId, rewardId, Constants.NO};
			commonDAO.update(sqlQuery, params);
		}
	}

	private List<String> getNewRewards(List<String> rewardIdsShouldAvailableForUser,
			List<String> currentUserAvailableRewards) {
			
		List<String> newRewards = new ArrayList<>();
		for(String rewardId : rewardIdsShouldAvailableForUser){
			if(!currentUserAvailableRewards.contains(rewardId)){
				newRewards.add(rewardId);
			}
		}
		
		return newRewards;
	}

	private List<String> getCurrentUserRewardIds(String userId) {
		String sqlQueryCurrentUserRewardIds = SQLQueries.FETCH_CURRENT_USER_REWARD_IDS_QUERY;
		Object[] params = {userId};
		List<String> currentUserAvailableRewards = commonDAO.fetchDataList(sqlQueryCurrentUserRewardIds, params);
		return currentUserAvailableRewards;
	}

	private List<String> getRewardIdsAvailableForUser(String userId) {
		String sqlQuery = SQLQueries.FETCH_REWARD_IDS_AVAILABLE_FOR_USER;
		Object[] params = {userId};
		List<String> rewardIdsShouldAvailableForUser = commonDAO.fetchDataList(sqlQuery, params);		
		return rewardIdsShouldAvailableForUser;
	}

	@Override
	public List<Map<String, Object>> searchReviewsByFilter(String companyName, String suburb, String rating, String review) {
		List<Map<String, Object>> data = new ArrayList<>();
		String sqlQuery = SQLQueries.SEARCH_REVIEWS_BY_FILTER_QUERY;
		
		try{
			if(companyName == null || suburb == null || rating == null || review == null){
				throw new Exception();
			}
		
			if(companyName.equalsIgnoreCase(Constants.ALL)){
				companyName = "";
			}
			if(suburb.equalsIgnoreCase(Constants.ALL)){
				suburb = "";
			}
			if(rating.equalsIgnoreCase(Constants.ALL)){
				rating = "";
			}
			if(review.equalsIgnoreCase(Constants.ALL)){
				review = "";
			}
			
			companyName = "%"+companyName+"%";
			suburb = "%"+ suburb +"%";
			rating = "%"+ rating +"%";
			review = "%"+ review +"%";
			
			Object[] params = {companyName, suburb, rating, review};
			data = commonDAO.fetch(sqlQuery, params);
			
			System.out.println("Company Name: "+companyName);
			System.out.println("Suburb: "+suburb);
			System.out.println("Rating: "+rating);
			System.out.println("Review: "+review);
			System.out.println("Data: "+data.toString());
			
		}catch(Exception e){
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			
			data.add(errorResponse);
		}
		return data;
	}

	@Override
	public List<Map<String, Object>> fetchAllUsersReviewsDetails() {
		List<Map<String, Object>> data = new ArrayList<>();
		List<Map<String, Object>> response = new ArrayList<>();
		String sqlQuery = SQLQueries.FETCH_ALL_USERS_REVIEWS_DETAILS_QUERY;
		String sqlQueryCompanyCategories = SQLQueries.FETCH_COMPANY_CATEGORIES_QUERY;
		String sqlQueryReviewInfluences = SQLQueries.FETCH_REVIEW_INFLUENCES_QUERY;
		
		try{
			
			data = commonDAO.fetch(sqlQuery);
			
			
			for(Map<String, Object> review : data ){
				String companyId = review.get(Constants.COMPANY_ID).toString();
				String reviewId = review.get(Constants.ID).toString();
				Object[] paramsRewiewInfluences = {reviewId};
				Object[] params = {companyId};
				List<String> categories = commonDAO.fetchDataList(sqlQueryCompanyCategories, params);
				List<String> influences = commonDAO.fetchDataList(sqlQueryReviewInfluences, paramsRewiewInfluences);
				review.put(Constants.COMPANY_CATEGORIES, categories);
				review.put(Constants.REVIEW_INFLUENCES, influences);
				response.add(review);
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			response.clear();
			response.add(errorResponse);
		}
		return response;
	}

	@Override
	public Map<String, Object> removeReviews(List<Map<String, String>> request) {
		Map<String, Object> response = new HashMap<>();
		String sqlQuery = SQLQueries.DELETE_FROM_SURVEY_ANSWERS_QUERY;
		String sqlQueryForSurveyAnswerInfluence = SQLQueries.DELETE_SURVEY_ANSWER_INFLUENCE_QUERY;
		try {

			for (Map<String, String> data : request) {
				String reviewId = data.get(Constants.REVIEW_ID);

				Object[] params = { reviewId };
				commonDAO.update(sqlQuery, params);
				commonDAO.update(sqlQueryForSurveyAnswerInfluence, params);
			}
			
			response.put(Constants.STATUS, Constants.OK);
			response.put(Constants.MESSAGE, Constants.INFLUENCES_REMOVED_SUCCESSFULLY);

		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
		}
		return response;

	}

	@Override
	public List<Map<String, Object>> fetchRecentReviewedCompaniesByUserId(String userId) {
		List<Map<String, Object>> response = new ArrayList<>();
		String sqlQuery = SQLQueries.FETCH_RECENT_REVIEWED_COMPANIES_BY_USER_ID;
		try{
			Object[] params = {userId, 3};
			response = commonDAO.fetch(sqlQuery, params);
		}catch(Exception e){
			e.printStackTrace();
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			response.clear();
			response.add(errorResponse);
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> fetchAllAustralianSuburbs() {
		List<Map<String, Object>> response = new ArrayList<>();
		String sqlQuery = SQLQueries.FETCH_ALL_AUSTRALIAN_SUBURBS_QUERY;
		try{
			response = commonDAO.fetch(sqlQuery);
		}catch(Exception e){
			e.printStackTrace();
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put(Constants.STATUS, Constants.ERROR);
			errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
			response.clear();
			response.add(errorResponse);
		}
		return response;
	}

}

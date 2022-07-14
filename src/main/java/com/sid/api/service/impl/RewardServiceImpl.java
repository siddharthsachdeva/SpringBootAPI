package com.sid.api.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.api.dao.CommonDAO;
import com.sid.api.pojo.Reward;
import com.sid.api.pojo.RewardPack;
import com.sid.api.services.RewardService;
import com.sid.api.utility.Constants;
import com.sid.api.utility.SQLQueries;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private CommonDAO commonDAO;


    //Charge for this one in future.
    @Transactional
    @Override
    public Map<String, String> addRewardPack(RewardPack rewardPack) {
        Map<String, String> response = new HashMap<>();
        String sqlQueryInsertIntoRewardPack = SQLQueries.INSERT_INTO_REWARD_PACK_QUERY;
        String sqlQueryInsertIntoRewards = SQLQueries.INSERT_INTO_APP_REWARDS_QUERY;
        try {

            String rewardPackId = UUID.randomUUID().toString();
            rewardPack.setId(rewardPackId);
            Object[] paramsRewardPack = {rewardPackId, rewardPack.getName(), rewardPack.getDescription(),
                    rewardPack.getLevel(), rewardPack.getCategory(), rewardPack.getRequiredReviewsNo()};
            commonDAO.update(sqlQueryInsertIntoRewardPack, paramsRewardPack);

            List<Reward> rewards = rewardPack.getRewards();

            for (Reward reward : rewards) {
                String rewardId = UUID.randomUUID().toString();
                Object[] paramsReward = {rewardId, reward.getCompany(), reward.getRewardDescription(),
                        reward.getType(), reward.getValidFrom(), reward.getValidTill(), reward.getCreatedDate(),
                        rewardPackId, reward.getExpiredDate(), reward.getRewardValue()};
                commonDAO.update(sqlQueryInsertIntoRewards, paramsReward);
            }

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARD_PACK_ADDED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return response;
    }

    @Transactional
    @Override
    public Map<String, String> addRewardsToRewardPack(List<Reward> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.INSERT_INTO_APP_REWARDS_QUERY;
        String sqlQueryValidate = SQLQueries.VALIDATE_REWARD_PACK_ID_QUERY;
        try {

            for (Reward reward : request) {

                String rewardPackId = reward.getRewardPacksId();
                Object[] paramsValidate = {rewardPackId};

                boolean result = commonDAO.validate(sqlQueryValidate, paramsValidate);

                if (!result) {
                    throw new Exception();
                }

                String id = UUID.randomUUID().toString();
                Object[] params = {id, reward.getCompany(), reward.getRewardDescription(), reward.getType(),
                        reward.getValidFrom(), reward.getValidTill(), reward.getCreatedDate(),
                        reward.getRewardPacksId(), reward.getExpiredDate(), reward.getRewardValue()};

                commonDAO.update(sqlQuery, params);

                response.put(Constants.STATUS, Constants.OK);
                response.put(Constants.MESSAGE, Constants.REWARDS_ADDED_TO_REWARD_PACK_SUCCESSFULLY);

            }

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> fetchRewardsByUserId(String userId) {
        List<Map<String, Object>> response = new ArrayList<>();
        String sqlQuery = SQLQueries.FETCH_REWARDS_BY_USER_ID_QUERY;
        try {
            Object[] params = {userId};
            response = commonDAO.fetch(sqlQuery, params);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(Constants.STATUS, Constants.ERROR);
            errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
            response.add(errorResponse);
        }

        return response;
    }

    @Transactional
    @Override
    public Map<String, String> removeRewardsFromRewardPack(List<Map<String, String>> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.DELETE_REWARD_FROM_APP_REWARDS_QUERY;
        String sqlQueryValidateRewardId = SQLQueries.VALIDATE_REWARD_ID_QUERY;
        try {

            for (Map<String, String> data : request) {
                String rewardId = data.get(Constants.REWARD_ID);
                Object[] paramsValidateRewardId = {rewardId};
                boolean result = commonDAO.validate(sqlQueryValidateRewardId, paramsValidateRewardId);
                if (!result) {
                    throw new Exception();
                }
                Object[] params = {rewardId};
                commonDAO.update(sqlQuery, params);
            }

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARDS_REMOVED_SUCCESSFULLY);
        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }

        return response;
    }

    @Transactional
    @Override
    public Map<String, String> assignRewardsToUser(List<Map<String, String>> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.INSERT_INTO_APP_USER_REWARDS_QUERY;

        try {
            for (Map<String, String> data : request) {
                String id = UUID.randomUUID().toString();
                String userId = data.get(Constants.USER_ID);
                String rewardId = data.get(Constants.REWARD_ID);
                String isExpired = data.get(Constants.IS_EXPIRED);
                String expiryDate = data.get(Constants.EXPIRY_DATE);
                String isClaimed = data.get(Constants.IS_CLAIMED);
                Object[] params = {id, userId, rewardId, isExpired, expiryDate, isClaimed};
                commonDAO.update(sqlQuery, params);
            }
            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARDS_ASSIGNED_SUCCESSFULLY);
        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }

        return response;
    }

    @Transactional
    @Override
    public Map<String, String> claimRewards(List<Map<String, String>> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.CLAIM_REWARD_QUERY;
        String sqlQueryReduceAvailableReward = SQLQueries.REDUCE_AVAILABLE_REWARD_QUERY;
        String sqlQueryRewardType = SQLQueries.FETCH_REWARD_TYPE_BY_ID_QUERY;
        try {
            //Reduce number of rewards by 1 if reward type is tap and collect.
            for (Map<String, String> data : request) {
                String rewardId = data.get(Constants.REWARD_ID);
                System.out.println("USER REWARD ID: " + rewardId);
                Object[] params = {Constants.YES, rewardId};
                int noOfRowsEffected = commonDAO.update(sqlQuery, params);
                System.out.println("User reward updated? : " + noOfRowsEffected);
                Object[] params2 = {rewardId};
                String type = commonDAO.fetchString(sqlQueryRewardType, params2);
                if (type.equalsIgnoreCase(Constants.TAP_AND_COLLECT)) {
                    commonDAO.update(sqlQueryReduceAvailableReward, params2);
                }
            }

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARD_CLAIMED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }

        return response;
    }

    @Override
    public List<Map<String, Object>> fetchFutureRewards(String userId) {
        List<Map<String, Object>> response = new ArrayList<>();
        String sqlQuery = SQLQueries.FETCH_FUTURE_REWARDS_QUERY;
        try {
            Object[] params = {userId};
            response = commonDAO.fetch(sqlQuery, params);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(Constants.STATUS, Constants.ERROR);
            errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
            response.add(errorResponse);
        }

        return response;
    }

    @Override
    public Map<String, String> addRewards(List<Reward> rewards) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.INSERT_INTO_APP_REWARDS_QUERY;
        try {

            for (Reward reward : rewards) {
				
			    /*Format formatter = new SimpleDateFormat("YYYY-MM-DD");
			    Date validFrom = new SimpleDateFormat().parse(reward.getValidFrom());
			    String validFromString = formatter.format(validFrom);*/

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date validFromDate = dateFormat.parse(reward.getValidFrom());
                String validFromString = new SimpleDateFormat("yyyy-MM-dd").format(validFromDate);

                Date validTillDate = dateFormat.parse(reward.getValidTill());
                String validTillString = new SimpleDateFormat("yyyy-MM-dd").format(validTillDate);
			    
			    /*Date validTill = new SimpleDateFormat().parse(reward.getValidTill());
			    String validTillString = formatter.format(validTill);*/

                Date expiredDate = dateFormat.parse(reward.getValidTill());
                String expiredDateString = new SimpleDateFormat("yyyy-MM-dd").format(expiredDate);
			    
			    /*Date expiredDate = new SimpleDateFormat().parse(reward.getExpiredDate());
			    String expiredDateString = formatter.format(expiredDate);*/

                String id = UUID.randomUUID().toString();
                Object[] params = {id, reward.getCompany(), reward.getRewardDescription(), reward.getType(),
                        validFromString, validTillString, reward.getCreatedDate(),
                        reward.getRewardPacksId(), expiredDateString, reward.getRewardValue(), reward.getNoOfRewards(),
                        reward.getMinimumReviews(), reward.getStatus(), reward.getIconUrl(), reward.getName(), reward.getCouponCode(), reward.getOfferUrl()};

                commonDAO.update(sqlQuery, params);
            }

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARDS_ADDED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
            e.printStackTrace();
        }

        return response;

    }

    @Override
    public List<Map<String, Object>> fetchAllRewards() {
        List<Map<String, Object>> response = new ArrayList<>();
        String sqlQuery = SQLQueries.FETCH_ALL_REWARDS_QUERY;
        try {
            response = commonDAO.fetch(sqlQuery);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(Constants.STATUS, Constants.ERROR);
            errorResponse.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
            response.add(errorResponse);
        }

        return response;
    }

    @Override
    public Map<String, String> toggleRewardsStatus(List<Map<String, String>> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.TOGGLE_REWARD_STATUS_QUERY;
        try {
            for (Map<String, String> map : request) {
                String id = map.get(Constants.REWARD_ID);
                Object[] params = {id};
                commonDAO.update(sqlQuery, params);
            }
            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARD_STATUS_UPDATED_SUCCESSFULLY);
        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return response;
    }

    @Override
    public Map<String, String> deleteRewards(List<Map<String, String>> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.DELETE_REWARD_QUERY;
        try {
            for (Map<String, String> map : request) {
                String id = map.get(Constants.REWARD_ID);
                Object[] params = {id};
                commonDAO.update(sqlQuery, params);
            }
            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARDS_DELETED);
        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return response;
    }

    @Override
    public Map<String, String> updateReward(Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String sqlQuery = SQLQueries.UPDATE_REWARD_QUERY;
        try {

            String rewardId = request.get(Constants.REWARD_ID);
            String name = request.get(Constants.NAME);
            String company = request.get(Constants.COMPANY);
            String rewardDescription = request.get(Constants.REWARD_DESCRIPTION);
            String type = request.get(Constants.TYPE);
            String validFrom = request.get(Constants.VALID_FROM);
            String validTill = request.get(Constants.VALID_TILL);
            String createdDate = request.get(Constants.CREATED_DATE);
            String rewardValue = request.get(Constants.REWARD_VALUE);
            String noOfRewards = request.get(Constants.NO_OF_REWARDS);
            String minimumReviews = request.get(Constants.MINIMUM_REVIEWS);
            String status = request.get(Constants.STATUS);
            String iconUrl = request.get(Constants.ICON_URL);
            String couponCode = request.get(Constants.COUPON_CODE);
            String offerUrl = request.get(Constants.OFFER_URL);

            Object[] params = {name, company, rewardDescription, type, validFrom, validTill, createdDate, rewardValue, noOfRewards, minimumReviews, status, iconUrl, couponCode, offerUrl, rewardId};
            commonDAO.update(sqlQuery, params);

            response.put(Constants.STATUS, Constants.OK);
            response.put(Constants.MESSAGE, Constants.REWARD_UPDATED_SUCCESSFULLY);

        } catch (Exception e) {
            response.put(Constants.STATUS, Constants.ERROR);
            response.put(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
            e.printStackTrace();
        }
        return response;

    }
}

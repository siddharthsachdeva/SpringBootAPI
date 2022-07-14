package com.sid.api.services;

import java.util.List;
import java.util.Map;

import com.sid.api.pojo.Reward;
import com.sid.api.pojo.RewardPack;

public interface RewardService {

	Map<String, String> addRewardsToRewardPack(List<Reward> request);
	List<Map<String, Object>> fetchRewardsByUserId(String userId);
	Map<String, String> addRewardPack(RewardPack rewardPack);
	Map<String, String> removeRewardsFromRewardPack(List<Map<String, String>> request);
	Map<String, String> assignRewardsToUser(List<Map<String, String>> request);
	Map<String, String> claimRewards(List<Map<String, String>> request);
	List<Map<String, Object>> fetchFutureRewards(String userId);
	Map<String, String> addRewards(List<Reward> rewards);
	List<Map<String, Object>> fetchAllRewards();
	Map<String, String> toggleRewardsStatus(List<Map<String, String>> request);
	Map<String, String> deleteRewards(List<Map<String, String>> request);
	Map<String, String> updateReward(Map<String, String> request);
}

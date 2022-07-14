package com.sid.api.controllers;

import java.util.List;
import java.util.Map;

import com.sid.api.pojo.Reward;
import com.sid.api.pojo.RewardPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.api.services.RewardService;

@CrossOrigin
@RequestMapping("/reward")
@RestController
public class RewardController {
	
	@Autowired
	private RewardService rewardService;
	
	@PostMapping("/addRewardPack")
	public Map<String, String> addRewardPack(@RequestBody RewardPack rewardPack){
		return rewardService.addRewardPack(rewardPack);
	}
	
	@PostMapping("/addRewards")
	public Map<String, String> addRewards(@RequestBody List<Reward> rewards){
		return rewardService.addRewards(rewards);
	}
	
	@PostMapping("/addRewardsToRewardPack")
	public Map<String, String> addRewardsToRewardPack(@RequestBody List<Reward> request){
		return rewardService.addRewardsToRewardPack(request);
	}
	
	@PostMapping("/removeRewardsFromRewardPack")
	public Map<String, String> removeRewardsFromRewardPack(@RequestBody List<Map<String, String>> request){
		return rewardService.removeRewardsFromRewardPack(request);
	}
	
	@PostMapping("/assignRewardsToUser")
	public Map<String, String> assignRewardsToUser(@RequestBody List<Map<String, String>> request){
		return rewardService.assignRewardsToUser(request);
	}
	
	@GetMapping("/fetchRewardsByUserID/{userId}")
	public List<Map<String, Object>> fetchRewardsByUserId(@PathVariable String userId){
		return rewardService.fetchRewardsByUserId(userId);
	}
	
	@PostMapping("/claimRewards")
	public Map<String, String> claimRewards(@RequestBody List<Map<String, String>> request){
		return rewardService.claimRewards(request);
	}
	
	@PostMapping("/toggleRewardsStatus")
	public Map<String, String> toggleRewardsStatus(@RequestBody List<Map<String, String>> request){
		return rewardService.toggleRewardsStatus(request);
	}
	
	@PostMapping("/deleteRewards")
	public Map<String, String> deleteRewards(@RequestBody List<Map<String, String>> request){
		return rewardService.deleteRewards(request);
	}
	
	@GetMapping("/fetchFutureRewards/{userId}")
	public List<Map<String, Object>> fetchFutureRewards(@PathVariable String userId){
		return rewardService.fetchFutureRewards(userId);
	}
	
	@GetMapping("/fetchAllRewards")
	public List<Map<String, Object>> fetchAllRewards(){
		return rewardService.fetchAllRewards();
	}

	@PostMapping("/updateReward")
	public Map<String, String> updateReward(@RequestBody Map<String, String> request){
		return rewardService.updateReward(request);
	}
	
}

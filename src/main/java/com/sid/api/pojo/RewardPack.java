package com.sid.api.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RewardPack {
	
	
	private String id;
	private String name;
	private String description;
	private String level;
	private String category;
	@JsonProperty("required_reviews_no")
	private String requiredReviewsNo;
	private List<Reward> rewards;
	
	public RewardPack(){
		
	}
	
/*	public RewardPack(String id, String name, String description, String type, String category, List<Reward> rewards) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.level = type;
		this.category = category;
		this.rewards = rewards;
	}
	
	public RewardPack(String name, String description, String level, String category, List<Reward> rewards) {
		super();
		this.name = name;
		this.description = description;
		this.level = level;
		this.category = category;
		this.rewards = rewards;
	}	
	*/
	
	

	public String getRequiredReviewsNo() {
		return requiredReviewsNo;
	}

	public void setRequiredReviewsNo(String requiredReviewsNo) {
		this.requiredReviewsNo = requiredReviewsNo;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Reward> getRewards() {
		return rewards;
	}

	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}

}

package com.sid.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reward {
	
	private String id;
	@JsonProperty("name")
	private String name;
	private String company;
	@JsonProperty("reward_description")
	private String rewardDescription;
	private String type;
	@JsonProperty("no_of_rewards")
	private String noOfRewards;
	@JsonProperty("minimum_reviews")
	private String minimumReviews;
	private String status;
	@JsonProperty("valid_from")
	private String validFrom;
	@JsonProperty("valid_till")
	private String validTill;
	@JsonProperty("created_date")
	private String createdDate;
	@JsonProperty("reward_packs_id")
	private String rewardPacksId;
	@JsonProperty("expired_date")
	private String expiredDate;
	@JsonProperty("reward_value")
	private String rewardValue;
	@JsonProperty("icon_url")
	private String iconUrl;
	@JsonProperty("coupon_code")
	private String couponCode;
	@JsonProperty("offer_url")
	private String offerUrl;
	
	
	
	public Reward(){
		
	}
	public Reward(String id, String name, String company, String rewardDescription, String type, String noOfRewards,
			String minimumReviews, String status, String validFrom, String validTill, String createdDate,
			String rewardPacksId, String expiredDate, String rewardValue, String iconUrl, String couponCode, String offerUrl) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.rewardDescription = rewardDescription;
		this.type = type;
		this.noOfRewards = noOfRewards;
		this.minimumReviews = minimumReviews;
		this.status = status;
		this.validFrom = validFrom;
		this.validTill = validTill;
		this.createdDate = createdDate;
		this.rewardPacksId = rewardPacksId;
		this.expiredDate = expiredDate;
		this.rewardValue = rewardValue;
		this.iconUrl = iconUrl;
		this.couponCode = couponCode;
		this.offerUrl = offerUrl;
	}
	
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getOfferUrl() {
		return offerUrl;
	}
	public void setOfferUrl(String offerUrl) {
		this.offerUrl = offerUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getNoOfRewards() {
		return noOfRewards;
	}

	public void setNoOfRewards(String noOfRewards) {
		this.noOfRewards = noOfRewards;
	}

	public String getMinimumReviews() {
		return minimumReviews;
	}

	public void setMinimumReviews(String minimumReviews) {
		this.minimumReviews = minimumReviews;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRewardDescription() {
		return rewardDescription;
	}
	public void setRewardDescription(String rewardDescription) {
		this.rewardDescription = rewardDescription;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTill() {
		return validTill;
	}
	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getRewardPacksId() {
		return rewardPacksId;
	}
	public void setRewardPacksId(String rewardPacksId) {
		this.rewardPacksId = rewardPacksId;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getRewardValue() {
		return rewardValue;
	}
	public void setRewardValue(String rewardValue) {
		this.rewardValue = rewardValue;
	}
}

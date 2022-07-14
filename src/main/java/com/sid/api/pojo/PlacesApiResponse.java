package com.sid.api.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacesApiResponse {

	@JsonIgnore
	@JsonProperty("html_attributions")
	private List<Object> html_attributions;

	@JsonIgnore
	@JsonProperty("results")
	private List<Result> results;

	@JsonProperty("status")
	private String status;

	public PlacesApiResponse() {

	}

	public List<Object> getHtml_attributions() {
		return html_attributions;
	}

	public void setHtml_attributions(List<Object> html_attributions) {
		this.html_attributions = html_attributions;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Location {

		@JsonProperty("lat")
		private double lat;

		@JsonProperty("lng")
		private double lng;

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Geometry {

		@JsonProperty("location")
		private Location location;

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class OpeningHours {

		@JsonProperty("open_now")
		private boolean open_now;

		@JsonProperty("weekday_text")
		private List<Object> weekday_text;

		public boolean isOpen_now() {
			return open_now;
		}

		public void setOpen_now(boolean open_now) {
			this.open_now = open_now;
		}

		public List<Object> getWeekday_text() {
			return weekday_text;
		}

		public void setWeekday_text(List<Object> weekday_text) {
			this.weekday_text = weekday_text;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Photo {

		@JsonProperty("height")
		private int height;

		@JsonProperty("html_attributions")
		private List<String> html_attributions;

		@JsonProperty("photo_reference")
		private String photo_reference;

		@JsonProperty("width")
		private int width;

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public List<String> getHtml_attributions() {
			return html_attributions;
		}

		public void setHtml_attributions(List<String> html_attributions) {
			this.html_attributions = html_attributions;
		}

		public String getPhoto_reference() {
			return photo_reference;
		}

		public void setPhoto_reference(String photo_reference) {
			this.photo_reference = photo_reference;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Result {

		@JsonProperty("geometry")
		private Geometry geometry;

		@JsonProperty("icon")
		private String icon;

		@JsonProperty("id")
		private String id;

		@JsonProperty("name")
		private String name;

		@JsonProperty("opening_hours")
		private OpeningHours opening_hours;

		@JsonProperty("photos")
		private List<Photo> photos;

		@JsonProperty("place_id")
		private String place_id;

		@JsonIgnore
		@JsonProperty("rating")
		private double rating;

		@JsonProperty("reference")
		private String reference;

		@JsonProperty("scope")
		private String scope;

		@JsonProperty("types")
		private List<String> types;

		@JsonProperty("vincity")
		private String vincity;

		public Geometry getGeometry() {
			return geometry;
		}

		public void setGeometry(Geometry geometry) {
			this.geometry = geometry;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
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

		public OpeningHours getOpening_hours() {
			return opening_hours;
		}

		public void setOpening_hours(OpeningHours opening_hours) {
			this.opening_hours = opening_hours;
		}

		public List<Photo> getPhotos() {
			return photos;
		}

		public void setPhotos(List<Photo> photos) {
			this.photos = photos;
		}

		public String getPlace_id() {
			return place_id;
		}

		public void setPlace_id(String place_id) {
			this.place_id = place_id;
		}

		public double getRating() {
			return rating;
		}

		public void setRating(double rating) {
			this.rating = rating;
		}

		public String getReference() {
			return reference;
		}

		public void setReference(String reference) {
			this.reference = reference;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public List<String> getTypes() {
			return types;
		}

		public void setTypes(List<String> types) {
			this.types = types;
		}

		public String getVicinity() {
			return vincity;
		}

		public void setVicinity(String vicinity) {
			this.vincity = vicinity;
		}

	}

}

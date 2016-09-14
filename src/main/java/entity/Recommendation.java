package entity;

import org.springframework.stereotype.Component;

@Component
public class Recommendation {

	private int bedroomCount;
	private int bathroomCount;
	private int squareFeet;
	private int recommendPrice;
	private int minPricePerSqrFeet;
	private int maxPricePerSqrFeet;

	public Recommendation() {}
	
	public Recommendation(int bedroomCount, int bathroomCount, int squareFeet, int recommendPrice, int minPricePerSqrFeet, int maxPricePerSqrFeet) {
		this.bedroomCount = bedroomCount;
		this.bathroomCount = bathroomCount;
		this.squareFeet = squareFeet;
		this.recommendPrice = recommendPrice;
		this.minPricePerSqrFeet = minPricePerSqrFeet;
		this.maxPricePerSqrFeet = maxPricePerSqrFeet;
	}
	
	public int getBedroomCount() {
		return bedroomCount;
	}
	public void setBedroomCount(int bedroomCount) {
		this.bedroomCount = bedroomCount;
	}
	public int getBathroomCount() {
		return bathroomCount;
	}
	public void setBathroomCount(int bathroomCount) {
		this.bathroomCount = bathroomCount;
	}
	public int getSquareFeet() {
		return squareFeet;
	}
	public void setSquareFeet(int squareFeet) {
		this.squareFeet = squareFeet;
	}
	public int getRecommendPrice() {
		return recommendPrice;
	}
	public void setRecommendPrice(int recommendPrice) {
		this.recommendPrice = recommendPrice;
	}
	public int getMinPricePerSqrFeet() {
		return minPricePerSqrFeet;
	}
	public void setMinPricePerSqrFeet(int minPricePerSqrFeet) {
		this.minPricePerSqrFeet = minPricePerSqrFeet;
	}
	public int getMaxPricePerSqrFeet() {
		return maxPricePerSqrFeet;
	}
	public void setMaxPricePerSqrFeet(int maxPricePerSqrFeet) {
		this.maxPricePerSqrFeet = maxPricePerSqrFeet;
	}
	
}

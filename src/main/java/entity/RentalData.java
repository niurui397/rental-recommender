package entity;

import org.springframework.stereotype.Component;

@Component("rentalData")
public class RentalData {

	private int bedroomCount;
	private int bathroomCount;
	private int squareFeet;
	private int price;
	
	public RentalData() {}
	
	public RentalData(int bedroomCount, int bathroomCount, int squareFeet, int price) {
		this.bedroomCount = bedroomCount;
		this.bathroomCount = bathroomCount;
		this.squareFeet = squareFeet;
		this.price = price;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}

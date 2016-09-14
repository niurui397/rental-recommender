package entity;

public class CoEfficients {
	
	private double bedroomCoef;
	private double bathroomCoef;
	private double squareFeetCoef;
	private double intercept;
	
	public CoEfficients(double bedroomCoef, double bathroomCoef, double squareFeetCoef, double intercept) {
		super();
		this.bedroomCoef = bedroomCoef;
		this.bathroomCoef = bathroomCoef;
		this.squareFeetCoef = squareFeetCoef;
		this.intercept = intercept;
	}
	
	public double getBedroomCoef() {
		return bedroomCoef;
	}
	public void setBedroomCoef(double bedroomCoef) {
		this.bedroomCoef = bedroomCoef;
	}
	public double getBathroomCoef() {
		return bathroomCoef;
	}
	public void setBathroomCoef(double bathroomCoef) {
		this.bathroomCoef = bathroomCoef;
	}
	public double getSquareFeetCoef() {
		return squareFeetCoef;
	}
	public void setSquareFeetCoef(double squareFeetCoef) {
		this.squareFeetCoef = squareFeetCoef;
	}
	public double getIntercept() {
		return intercept;
	}
	public void setIntercept(double intercept) {
		this.intercept = intercept;
	}
	
	

}

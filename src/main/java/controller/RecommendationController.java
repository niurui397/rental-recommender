package controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entity.Recommendation;
import entity.RentalData;
import services.RentalDataService;

@RestController
public class RecommendationController {
	
	@Autowired
	RentalDataService rentalDataService;
	
	Logger log = Logger.getLogger(RecommendationController.class);

	@CrossOrigin
	@RequestMapping(value="/recommendation", method=RequestMethod.GET)
	public ResponseEntity<Recommendation> recommendation(@RequestParam(value="bedroomCount", required=true) int bedroomCount, @RequestParam(value="bathroomCount", required=true) int bathroomCount, @RequestParam(value="squareFeet", required=true) int squareFeet) {
		RentalData rentalData = new RentalData(bedroomCount, bathroomCount, squareFeet, -1);
		Recommendation recommendation = rentalDataService.getRecommendataion(rentalData);
		return new ResponseEntity<Recommendation>(recommendation, HttpStatus.OK);
	}
	
}

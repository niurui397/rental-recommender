package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import entity.Recommendation;
import entity.RentalData;

@Service
public interface RentalDataService {

	public List<RentalData> parseCSV(String filePath) throws IOException;
	public void storeRentalDataFromCSV(String csvFilePath) throws IOException, SQLException;
	public void storeRentalData(List<RentalData> rentalDataList) throws IOException, SQLException;
//	public Recommender buildRecommender(String csvFilePath);
//	public Recommender buildRecommender(List<RentalData> rentalDataList);
	public Recommendation getRecommendataion(RentalData rentalData);
	
}

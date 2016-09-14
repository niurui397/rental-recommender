package services.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.RentalDataDAO;
import dao.impl.RentalDataJdbcDAOImpl;
import entity.CoEfficients;
import entity.Recommendation;
import entity.RentalData;
import services.RentalDataService;

@Component
public class RentalDataServiceImpl implements RentalDataService {
	
	@Autowired
	RentalDataDAO rentalDataDAO;
	
	Logger log = Logger.getLogger(RentalDataJdbcDAOImpl.class);

	@Override
	public void storeRentalDataFromCSV(String csvFilePath) throws IOException, SQLException {
		List<RentalData> rentalDataList = parseCSV(csvFilePath);
		rentalDataDAO.saveRentalDataList(rentalDataList);
	}
	
	@Override
	public void storeRentalData(List<RentalData> rentalDataList) throws IOException, SQLException {
		rentalDataDAO.saveRentalDataList(rentalDataList);
	}

	@Override
	public Recommendation getRecommendataion(RentalData rentalData) {
		Recommendation recommendation = null;
		CoEfficients coef = null;
		double minPricePerSqft = -1;
		double maxPricePerSqft = -1;
		try {
			coef = rentalDataDAO.getCoEfficients();
			minPricePerSqft = rentalDataDAO.getMinPerSqft(rentalData);
			maxPricePerSqft = rentalDataDAO.getMaxPerSqft(rentalData);
		} catch (SQLException e) {
			log.error("Error getting coefficients from database", e);
		}
		if (coef == null) {
			coef = getCoEfficientsFromPythonScriptResult();
			try {
				minPricePerSqft = rentalDataDAO.getMinPerSqft(rentalData);
				maxPricePerSqft = rentalDataDAO.getMaxPerSqft(rentalData);
				if (coef != null) {
					rentalDataDAO.saveCoEfficients(coef);
				}
			} catch (SQLException e) {
				log.error("Error saving coefficients to database", e);
			}
			int recommendPrice = (int) calculateRecommendationPrice(rentalData, coef);
			recommendation = new Recommendation(rentalData.getBedroomCount(), rentalData.getBathroomCount(), rentalData.getSquareFeet(), recommendPrice, minPricePerSqft, maxPricePerSqft);
		}
		return recommendation;
	}

	@Override
	public List<RentalData> parseCSV(String filePath) throws IOException {
		List<RentalData> rentalDataList = new ArrayList<>();
		Reader in = new FileReader(filePath);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
		for (CSVRecord record : records) {
			RentalData rentalData = new RentalData();
			rentalData.setBedroomCount((int)Double.parseDouble(record.get("bedrooms")));
			rentalData.setBathroomCount((int)Double.parseDouble(record.get("bathrooms")));
			rentalData.setSquareFeet((int)Double.parseDouble(record.get("square-foot")));
			rentalData.setPrice((int)Double.parseDouble(record.get("price")));
			rentalDataList.add(rentalData);
		}
		return rentalDataList;
	}
	
	private CoEfficients getCoEfficientsFromPythonScriptResult() {
		BufferedReader br = null;
		CoEfficients coef = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("coefficients.txt"));
			if ((sCurrentLine = br.readLine()) != null) {
				String[] tokens = sCurrentLine.split(",");
				double bedroomCoef = Double.parseDouble(tokens[0].trim());
				double bathroomCoef = Double.parseDouble(tokens[1].trim());
				double squareFeetCoef = Double.parseDouble(tokens[2].trim());
				double intercept = Double.parseDouble(tokens[3].trim());
				coef = new CoEfficients(bedroomCoef, bathroomCoef, squareFeetCoef, intercept);
			}
		} catch (IOException e) {
			log.error("Error to read coefficients from coefficients.txt", e);
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
			}
		}
		return coef;
	}
	
	private double calculateRecommendationPrice(RentalData rentalData, CoEfficients coef) {
		return rentalData.getBedroomCount() * coef.getBedroomCoef()
				+ rentalData.getBedroomCount() * coef.getBathroomCoef()
				+ rentalData.getSquareFeet() * coef.getSquareFeetCoef()
				+ coef.getIntercept();
	}

}

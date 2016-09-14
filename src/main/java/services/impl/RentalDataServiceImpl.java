package services.impl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.RentalDataDAO;
import entity.Recommendation;
import entity.RentalData;
import services.RentalDataService;

@Component
public class RentalDataServiceImpl implements RentalDataService {
	
	@Autowired
	RentalDataDAO rentalDataDAO;

	@Override
	public void storeRentalDataFromCSV(String csvFilePath) throws IOException, SQLException {
		List<RentalData> rentalDataList = parseCSV(csvFilePath);
		rentalDataDAO.insertRentalDataList(rentalDataList);
	}
	
	@Override
	public void storeRentalData(List<RentalData> rentalDataList) throws IOException, SQLException {
		rentalDataDAO.insertRentalDataList(rentalDataList);
	}

	@Override
	public Recommendation getRecommendataion(RentalData rentalData) {
		return new Recommendation(rentalData.getBedroomCount(), 
								rentalData.getBathroomCount(), 
								rentalData.getSquareFeet(),
								1000, 12, 30);
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

//	@Override
//	public Recommender buildRecommender(String csvFilePath) {
//		Recommender recommender = null;
//		MemoryIDMigrator thing2long = new MemoryIDMigrator();
//		DataModel dataModel = null;
//		Map<Long,List<Preference>> preferecesOfUsers = new HashMap<Long,List<Preference>>();
//
//		List<RentalData> rentalDataList = parseCSV(csvFilePath);
//
//		for (RentalData rentalData : rentalDataList) {
//			List<Preference> userPrefList;
//
//			String person = line[0];
//			String likeName = line[1];
//
//			// store the mapping for the user
//			long userLong = thing2long.toLongID(person);
//			thing2long.storeMapping(userLong, person);
//
//			// store the mapping for the item
//			long itemLong = thing2long.toLongID(likeName);
//			thing2long.storeMapping(itemLong, likeName);
//
//			if((userPrefList = preferecesOfUsers.get(userLong)) == null) {
//				userPrefList = new ArrayList<Preference>();
//				preferecesOfUsers.put(userLong, userPrefList);
//			}
//			// add the similarities found to this user
//			userPrefList.add(new GenericPreference(userLong, itemLong, 1));
//		}
//
//		// create the corresponding mahout data structure from the map
//		FastByIDMap<PreferenceArray> preferecesOfUsersFastMap = new FastByIDMap<PreferenceArray>();
//		for(Entry<Long, List<Preference>> entry : preferecesOfUsers.entrySet()) {
//			preferecesOfUsersFastMap.put(entry.getKey(), new GenericUserPreferenceArray(entry.getValue()));
//		}
//
//		// create a data model
//		dataModel = new GenericDataModel(preferecesOfUsersFastMap);
//
//		// Recommender Instantiation
//		return new GenericBooleanPrefItemBasedRecommender(dataModel, new LogLikelihoodSimilarity(dataModel));
//	}

//	@Override
//	public Recommender buildRecommender(List<RentalData> rentalDataList) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}

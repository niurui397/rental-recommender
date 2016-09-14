package dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import entity.RentalData;

@Repository
public interface RentalDataDAO {

	public void insertRentalDataList(List<RentalData> rentalDataList) throws SQLException;
	public List<RentalData> getQualifiedRentalData(int bedroomCount, int bathroomCount) throws SQLException;
	
}

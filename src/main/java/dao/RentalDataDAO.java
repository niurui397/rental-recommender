package dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import entity.CoEfficients;
import entity.RentalData;

@Repository
public interface RentalDataDAO {

	public void saveRentalDataList(List<RentalData> rentalDataList) throws SQLException;
	public List<RentalData> getQualifiedRentalData(int bedroomCount, int bathroomCount) throws SQLException;
	public CoEfficients getCoEfficients() throws SQLException;
	public void saveCoEfficients(CoEfficients coef) throws SQLException;
	public double getMinPerSqft(RentalData rentalData) throws SQLException;
	public double getMaxPerSqft(RentalData rentalData) throws SQLException;
	
}

package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.RentalDataDAO;
import entity.RentalData;

@Component
public class RentalDataJdbcDAOImpl implements RentalDataDAO {
	
	Logger log = Logger.getLogger(RentalDataJdbcDAOImpl.class);
	
	@Autowired
	private DataSource dataSource;

	@Override
	public void insertRentalDataList(List<RentalData> rentalDataList) throws SQLException {
		// hard code SQL query here, in production, the SQL queries are in
		// resource files
		String insertSql = "INSERT INTO RENTAL_DATA (BEDROOM_COUNT, BATHROOM_COUNT, SQUARE_FEET, PRICE) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql);
			for (RentalData rentalData : rentalDataList) {
				int i = 1;
				preparedStatement.setInt(i++, rentalData.getBedroomCount());
				preparedStatement.setInt(i++, rentalData.getBathroomCount());
				preparedStatement.setInt(i++, rentalData.getSquareFeet());
				preparedStatement.setInt(i++, rentalData.getPrice());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			log.error("Error inserting rental data list", e);
			conn.rollback();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<RentalData> getQualifiedRentalData(int bedroomCount, int bathroomCount) throws SQLException {
		// hard code SQL query here, in production, the SQL queries are in
		// resource files
		List<RentalData> rentalDataList = new ArrayList<>();
		String qualifiedQuery = "SELECT * FROM RENTAL_DATA WHERE BEDROOM_COUNT = ? AND BATHROOM_COUNT = ?";
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(qualifiedQuery);
			preparedStatement.setInt(1, bedroomCount);
			preparedStatement.setInt(2, bathroomCount);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				rentalDataList.add(new RentalData(
						resultSet.getInt("BEDROOM_COUNT"),
						resultSet.getInt("BATHROOM_COUNT"),
						resultSet.getInt("SQUARE_FEET"),
						resultSet.getInt("PRICE")));
			}
			resultSet.close();
		} catch (SQLException e) {
			log.error("Error retrieving rental data list", e);
			conn.rollback();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return rentalDataList;
	}

}

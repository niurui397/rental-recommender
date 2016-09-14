package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.RentalDataDAO;
import entity.CoEfficients;
import entity.RentalData;

@Component
public class RentalDataJdbcDAOImpl implements RentalDataDAO {
	
	Logger log = Logger.getLogger(RentalDataJdbcDAOImpl.class);
	
	@Autowired
	private DataSource dataSource;

	@Override
	public void saveRentalDataList(List<RentalData> rentalDataList) throws SQLException {
		// hard code SQL query here, in production, the SQL queries are in
		// resource files
		String insertSql = "INSERT INTO RENTAL_DATA (BEDROOM_COUNT, BATHROOM_COUNT, SQUARE_FEET, PRICE, PRICE_PER_SQFT) VALUES (?, ?, ?, ?, ?)";
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
				preparedStatement.setDouble(i++, (double)rentalData.getPrice() / rentalData.getSquareFeet());
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

	@Override
	public CoEfficients getCoEfficients() throws SQLException {
		CoEfficients coef = null;
		String query = "SELECT * FROM COEFFICIENTS";
		Statement statement = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				coef = new CoEfficients(resultSet.getDouble("BEDROOM_COEF"),
										resultSet.getDouble("BATHROOM_COEF"),
										resultSet.getDouble("SQUARE_FEET_COEF"),
										resultSet.getDouble("INTERCEPT"));
			}
			resultSet.close();
		} catch (SQLException e) {
			log.error("Error retrieving rental data list", e);
			conn.rollback();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return coef;
	}

	@Override
	public void saveCoEfficients(CoEfficients coef) throws SQLException {
		String insertSql = "INSERT INTO COEFFICIENTS (BEDROOM_COEF, BATHROOM_COEF, SQUARE_FEET_COEF, INTERCEPT) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql);
			int i = 1;
			preparedStatement.setDouble(i++, coef.getBedroomCoef());
			preparedStatement.setDouble(i++, coef.getBathroomCoef());
			preparedStatement.setDouble(i++, coef.getSquareFeetCoef());
			preparedStatement.setDouble(i++, coef.getIntercept());
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
	public double getMinPerSqft(RentalData rentalData) throws SQLException {
		double minPricePerSqft = -1;
		String query = "SELECT * FROM RENTAL_DATA WHERE PRICE_PER_SQFT =  ( SELECT MIN(PRICE_PER_SQFT) FROM RENTAL_DATA where BEDROOM_COUNT = ? and BATHROOM_COUNT = ?)";
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(query);
			int i = 1;
			preparedStatement.setInt(i++, rentalData.getBedroomCount());
			preparedStatement.setInt(i++, rentalData.getBathroomCount());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				minPricePerSqft = resultSet.getDouble("PRICE_PER_SQFT");
			}
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
		return minPricePerSqft;
	}

	@Override
	public double getMaxPerSqft(RentalData rentalData) throws SQLException {
		double maxPricePerSqft = -1;
		String query = "SELECT * FROM RENTAL_DATA WHERE PRICE_PER_SQFT =  ( SELECT MAX(PRICE_PER_SQFT) FROM RENTAL_DATA where BEDROOM_COUNT = ? and BATHROOM_COUNT = ?)";
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(query);
			int i = 1;
			preparedStatement.setInt(i++, rentalData.getBedroomCount());
			preparedStatement.setInt(i++, rentalData.getBathroomCount());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				maxPricePerSqft = resultSet.getDouble("PRICE_PER_SQFT");
			}
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
		return maxPricePerSqft;
	}

}

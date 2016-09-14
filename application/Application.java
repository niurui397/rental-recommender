package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import services.RentalDataService;

@SpringBootApplication
@ImportResource("applicationContext.xml")
public class Application {
	
	static Logger log = Logger.getLogger(Application.class);
	
	@Autowired
	private RentalDataService rentalDataService;

	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
	public void initDB() {
		try {
			rentalDataService.storeRentalDataFromCSV("challenge_data.csv");
		} catch (IOException e) {
			log.error("Error loading dataset", e);
		} catch (SQLException e) {
			log.error("Error storing dataset to database", e);
		}
	}
	
}

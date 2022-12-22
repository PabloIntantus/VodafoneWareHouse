package com.vodafone.warehouse;

import com.vodafone.warehouse.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The type Vodafone application.
 */
@SpringBootApplication
@EnableMongoRepositories
public class WarehouseApplication {

	/**
	 * The Device repository.
	 */
	@Autowired
	DeviceRepository deviceRepository;

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}

}

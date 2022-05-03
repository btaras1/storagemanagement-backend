package com.management.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.management.storage.model"})  // scan JPA entities
public class StorageBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageBackendApplication.class, args);
	}

}

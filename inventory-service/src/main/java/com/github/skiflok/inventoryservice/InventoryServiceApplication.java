package com.github.skiflok.inventoryservice;

import com.github.skiflok.inventoryservice.model.Inventory;
import com.github.skiflok.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setScuCode("iphone_13");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory1.setScuCode("iphone_13_red");
			inventory1.setQuantity(1);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}

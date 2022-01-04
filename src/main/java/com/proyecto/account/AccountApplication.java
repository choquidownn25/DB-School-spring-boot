package com.proyecto.account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.proyecto.account.repository.StorageService;
import com.proyecto.account.util.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AccountApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
		LOGGER.error("Message logged at ERROR level");
		LOGGER.warn("Message logged at WARN level");
		LOGGER.info("Message logged at INFO level");
		LOGGER.debug("Message logged at DEBUG level");
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}

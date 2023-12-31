package com.fasttrack.filapedidos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableScheduling
@RestController
@SpringBootApplication
public class FilaPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilaPedidosApplication.class, args);
		log.info("API started successfully!");
	}
}

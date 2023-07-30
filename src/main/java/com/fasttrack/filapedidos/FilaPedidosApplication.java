package com.fasttrack.filapedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FilaPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilaPedidosApplication.class, args);
	}
}

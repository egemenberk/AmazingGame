package com.ceng453.Server;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@EnableJpaRepositories("com.ceng453.Server")
@EntityScan("com.ceng453.Server")
public class ServerApplication {

	public static void main(String[] args) {
		run(ServerApplication.class, args);
	}
}

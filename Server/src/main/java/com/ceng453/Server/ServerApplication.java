package com.ceng453.Server;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@EnableJpaRepositories("com.ceng453.Server.repositories")
@EntityScan("com.ceng453.Server.entities")
public class ServerApplication {

	public static void main(String[] args) {
		run(ServerApplication.class, args);
	}
}

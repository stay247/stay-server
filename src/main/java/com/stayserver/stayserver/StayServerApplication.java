package com.stayserver.stayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = "com.stayserver.stayserver.repository.redis")
public class StayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayServerApplication.class, args);
	}

}


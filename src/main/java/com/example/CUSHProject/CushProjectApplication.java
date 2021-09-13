package com.example.CUSHProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
public class CushProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CushProjectApplication.class, args);
	}

}

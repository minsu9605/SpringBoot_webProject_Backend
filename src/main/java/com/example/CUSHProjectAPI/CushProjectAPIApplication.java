package com.example.CUSHProjectAPI;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableBatchProcessing //Spring Batch 기능 활성화
@EnableScheduling
@SpringBootApplication
public class CushProjectAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(CushProjectAPIApplication.class, args);
	}

}

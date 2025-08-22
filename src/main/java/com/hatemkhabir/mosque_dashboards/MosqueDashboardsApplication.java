package com.hatemkhabir.mosque_dashboards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MosqueDashboardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MosqueDashboardsApplication.class, args);
	}

}

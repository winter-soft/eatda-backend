package com.proceed.swhackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class SwhackathonApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

//	@PostConstruct
//	public void started(){
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//		System.out.println("현재시간 : " + new Date());
//	}

	public static void main(String[] args) {
		SpringApplication.run(SwhackathonApplication.class, args);
	}

}

package com.smacc.challenge.failover.mailer;

import com.k4hub.swagger2.EnableSwagger2Tools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableSwagger2Tools
@EnableRetry
@SpringBootApplication
public class MailerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailerApplication.class, args);
	}
}

package com.smacc.challenge.failover.mailer;

import com.k4hub.swagger2.EnableSwagger2Tools;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableSwagger2Tools
@SpringBootApplication
@EnableEncryptableProperties
@EnableRetry
public class MailerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailerApplication.class, args);
    }
}

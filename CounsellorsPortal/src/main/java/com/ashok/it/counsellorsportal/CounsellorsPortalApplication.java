package com.ashok.it.counsellorsportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CounsellorsPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CounsellorsPortalApplication.class, args);
    }

}

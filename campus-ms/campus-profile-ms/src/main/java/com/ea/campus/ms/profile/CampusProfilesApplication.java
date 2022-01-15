package com.ea.campus.ms.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackageClasses = {CampusProfilesApplication.class})
public class CampusProfilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusProfilesApplication.class, args);
    }

}

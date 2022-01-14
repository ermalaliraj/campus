package com.ea.campus.ms.student;

import com.ea.campus.ms.student.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoAuditing
@EnableMongoRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@SpringBootApplication(scanBasePackageClasses = { CampusStudentApplication.class })
public class CampusStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusStudentApplication.class, args);
    }

}

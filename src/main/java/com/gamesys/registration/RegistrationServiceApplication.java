package com.gamesys.registration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
public class RegistrationServiceApplication implements CommandLineRunner {

    @Autowired
    private RegistrationServiceStartup startup;

    public static void main(String[] args) {
        SpringApplication.run(RegistrationServiceApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        startup.initialize();
    }

}

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        // Dotenv dotenv = Dotenv.load();

        // // Set database connection properties
        // System.setProperty("spring.datasource.url", "jdbc:mysql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") + "/" + dotenv.get("DB_NAME"));
        // System.setProperty("spring.datasource.username", dotenv.get("DB_USER"));
        // System.setProperty("spring.datasource.password", dotenv.get("DB_PASS"));
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoApplication.class);
    }
}

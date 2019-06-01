package com.example.permissions.config;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrationConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee("mongodb://" + host + ":" + port + "/" + database);
        runner.setDbName(database);
        runner.setChangeLogsScanPackage(
                "com.example.permissions.migration");

        return runner;
    }

}

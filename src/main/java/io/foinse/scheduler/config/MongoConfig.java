package io.foinse.scheduler.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
    @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb://scheduler-app:schedulerzxcv@18.223.151.230:27017/");
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "scheduler");
    }
}

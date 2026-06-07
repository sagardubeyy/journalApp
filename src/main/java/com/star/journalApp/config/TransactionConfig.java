package com.star.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    //ye tranctional me help krta hai bean bna k bta
    @Bean
    public PlatformTransactionManager abc(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
}

package com.legwand.nodemonitor.config;

import com.legwand.nodemonitor.service.NodesServiceImpl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:application.properties")
@EnableMongoRepositories
public class MongoConfig {

    private final static Logger LOG = Logger.getLogger(MongoConfig.class.getName());

    @Value("${mongodb.connectionString}")
    private String mongoDbConnectionString;

    @Value("${mongodb.database}")
    private String mongoDatabase;

    @PostConstruct
    void init() {
        LOG.info(MessageFormat.format("Mongo connection string: {0}", mongoDbConnectionString));
        LOG.info(MessageFormat.format("Mongo database: {0}", mongoDatabase));
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoDbConnectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoDatabase);
    }

}

package org.example.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(basePackages = "org.example.model.author", mongoTemplateRef = "mongoTemplate")
@EnableConfigurationProperties
public class MongoPersistenceAutoConfiguration {

    @Value("${app.datasource2.username}")
    private String username;

    @Value("${app.datasource2.password}")
    private String password;

    @Value("${app.datasource2.database}")
    private String db;

    @Value("${app.datasource2.authentication-database}")
    private String authenticationDatabase;

    @Value("${app.datasource2.port}")
    private int port;

    @Value("${app.datasource2.host}")
    private String host;


    @Bean(name = "mongoClient")
    public MongoClient mongoClient() {

        MongoCredential credential = MongoCredential
                .createCredential(username, authenticationDatabase, password.toCharArray());

        return MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(singletonList(new ServerAddress(host, port))))
                .credential(credential)
                .build());
    }

    @Bean(name = "mongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("mongoClient") MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, db);
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("mongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

}
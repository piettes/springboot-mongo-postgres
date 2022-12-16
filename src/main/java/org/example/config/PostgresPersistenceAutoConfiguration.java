package org.example.config;

import org.example.model.book.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "org.example.model.book",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager")
public class PostgresPersistenceAutoConfiguration {

    @Value("${app.datasource1.url}")
    private String datasourceUrl;

    @Value("${app.datasource1.username}")
    private String username;

    @Value("${app.datasource1.password}")
    private String password;

    @Primary
    @Bean
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Primary
    @Bean("postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(postgresDataSource()).packages(Book.class).build();
    }

    @Primary
    @Bean("postgresTransactionManager")
    public PlatformTransactionManager bookTransactionManager(
            final @Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory
    ) {
        return new JpaTransactionManager(postgresEntityManagerFactory.getObject());

    }
}
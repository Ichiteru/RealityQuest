package com.chern.config;

import com.chern.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.chern.repo")
@PropertySource("classpath:/application.properties")
public class JdbcConfig {

    @Value("${db.driverClassName}")
    private String driverName;
    @Value("${db.password}")
    private String password;
    @Value("${db.username}")
    private String username;
    @Value("${db.url}")
    private String url;
    @Value("${db.initialPoolSize}")
    private int initialPoolSize;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource factory = new DriverManagerDataSource();
        factory.setDriverClassName(driverName);
        factory.setPassword(password);
        factory.setUsername(username);
        factory.setUrl(url);
        ConnectionPool dataSource = new ConnectionPool(factory, initialPoolSize);
        return dataSource;
    }

}

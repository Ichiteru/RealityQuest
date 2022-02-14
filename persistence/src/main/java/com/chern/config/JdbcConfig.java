package com.chern.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
@ComponentScan("com.chern.dao")
public class JdbcConfig {

    public DataSource postgreSQLDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("postgre");
        dataSource.setPassword("root");
        dataSource.setUrl("url");
        dataSource.setUsername("postgres");
        
        return dataSource;
    }
}

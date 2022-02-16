package com.chern.config;

import com.chern.repo.CustomDataSource;
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

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean(name = "customDataSource")
    public DataSource customDataSource(){
        CustomDataSource dataSource = new CustomDataSource();
        return dataSource;
    }
}

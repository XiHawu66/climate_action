package com.climate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JPAConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("JiantongJi");
        dataSource.setUrl(
                "jdbc:mysql://localhost:3306/carbonique?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");

        return dataSource;
    }

}

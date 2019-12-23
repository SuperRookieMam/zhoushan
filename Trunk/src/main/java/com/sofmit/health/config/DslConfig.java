package com.sofmit.health.config;

import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

@Configuration
@Order(200)
public class DslConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SQLQueryFactory getSqlQueryFactory() {
        return new SQLQueryFactory(new com.querydsl.sql.Configuration(new MySQLTemplates()), dataSource);
    }

}

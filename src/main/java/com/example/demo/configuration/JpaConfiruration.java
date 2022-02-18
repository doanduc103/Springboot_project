package com.example.demo.configuration;

import com.example.demo.service.ProductService;
import com.example.demo.service.serviceImpl.ProductServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.demo.repository"})
@ComponentScan("com.example.demo")
@PropertySource("classpath:application.properties")
public class JpaConfiruration {

    public ProductService productService(){
        return new ProductServiceImpl();
    }
}

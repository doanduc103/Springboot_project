//
//package com.example.demo.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//public class PersistenceJPAConfig {
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
//		return null;
//
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionmanager() {
//		JpaTransactionManager transactionmanager = new JpaTransactionManager();
//		transactionmanager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
//		return transactionmanager;
//	}
//}

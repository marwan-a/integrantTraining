package com.javatpoint;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.boot.test.context.TestConfiguration
@EnableJpaRepositories(basePackages = "com.javatpoint.repositories")
@TestPropertySource("application.yml")
@EnableTransactionManagement
public class TestConfiguration {
	   @Bean
	   @Primary
	   public DataSource dataSource(){
	       DriverManagerDataSource dataSource = new DriverManagerDataSource();
	       dataSource.setDriverClassName("org.h2.Driver");
	       dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
	       dataSource.setUsername( "sa" );
	       dataSource.setPassword( "" );
	       return dataSource;
	   }
	 @Bean
	 @Primary
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean em 
	        = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(dataSource());
	      em.setPackagesToScan(new String[] { "com.javatpoint.models", "com.javatpoint.verification" });
	 
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      em.setJpaProperties(additionalProperties());
	 
	      return em;
	   }
	@Bean
	@Primary
	   public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	       JpaTransactionManager transactionManager = new JpaTransactionManager();
	       transactionManager.setEntityManagerFactory(emf);
	    
	       return transactionManager;
	   }
	@Bean
	@Primary
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	       return new PersistenceExceptionTranslationPostProcessor();
	   }
	    
	   Properties additionalProperties() {
	       Properties properties = new Properties();	
	       properties.setProperty("hibernate.hbm2ddl.auto", "create");
//	       properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//	       properties.setProperty("hibernate.flushMode", "FLUSH_AUTO");    
//	       properties.setProperty("hibernate.id.new_generator_mappings", "false");    
	       return properties;
	   }
}

package com.todonotes.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.todonotes.userservice.model.UserModel;
import com.todonotes.utils.EmailSender;

@Configuration
public class ApplicationConfig {
	
	@Bean
    Queue queue() {
        return new Queue("email-queue", false);
    }
	
	@Bean
    TopicExchange exchange() {
        return new TopicExchange("email-exchange");
    }
	
	@Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("email-key");
    }
	
	@Bean
    MessageListenerAdapter listenerAdapter(EmailSender emailSender) {
        return new MessageListenerAdapter(emailSender, "receiveMessage");
    }
	
	@Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("email-queue");
        container.setMessageListener(listenerAdapter);
        return container;
    }
	
	@Bean
	public DataSource dataSource() 
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/todonotes");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
 
	@Bean
	public LocalSessionFactoryBean sessionFactory() 
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.todonotes");
		sessionFactory.setAnnotatedClasses(UserModel.class);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.put("hibernate.cache.use_second_level_cache", true);
		sessionFactory.setHibernateProperties(hibernateProperties);
		return sessionFactory;
	}
 
	@Bean
	public HibernateTransactionManager transactionManager() 
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() 
	{
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.todonotes" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		return em;
	}
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}

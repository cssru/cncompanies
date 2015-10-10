package com.cssru.companies.config;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Configuration
@EnableWebMvc
@ComponentScan("com.cssru.companies")
@EnableTransactionManagement
@EnableScheduling
@Import({SecurityConfig.class, GlobalMethodSecurityConfig.class})
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/resources/fonts/");
        registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
        registry.addResourceHandler("/html/**").addResourceLocations("/resources/html/");
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        // only path extension is taken into account
        configurer.favorPathExtension(true).
                ignoreAcceptHeader(true).
                useJaf(true);
    }

    @Bean
    public ViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public MappingJackson2JsonView setupMappingJackson2JsonView() {
        return new MappingJackson2JsonView();
    }


    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsl = new JndiDataSourceLookup();
        dsl.setResourceRef(true);
        DataSource dataSource = dsl.getDataSource("chiefnotes");
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean result = new LocalSessionFactoryBean();
        result.setDataSource(dataSource());
        result.setPackagesToScan("com.cssru.companies.domain");
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.show_statistics", true);
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        result.setHibernateProperties(hibernateProperties);
        result.afterPropertiesSet();
        return result.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        HibernateTransactionManager result = new HibernateTransactionManager(sessionFactory());
        return result;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    @DependsOn("propertyPlaceholderConfigurer")
    public MailSender getMailSender(
            @Value("${mail.login}") String mailLogin,
            @Value("${mail.password}") String mailPassword) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSender.setJavaMailProperties(props);
        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername(mailLogin);
        mailSender.setPassword(mailPassword);
        mailSender.setPort(587);
        return mailSender;
    }

    @Bean(name = "propertyPlaceholderConfigurer")
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        ClassPathResource[] resources = new ClassPathResource[]{
                new ClassPathResource("credentials.properties")
        };
        configurer.setLocations(resources);
        return configurer;
    }

}

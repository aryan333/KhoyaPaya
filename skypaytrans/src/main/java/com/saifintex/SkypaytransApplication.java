package com.saifintex;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.saifintex.config.AjaxCallEntryPoint;
import com.saifintex.validators.MessageValidator;
import com.saifintex.validators.UserLoginValidator;
import com.saifintex.validators.UserSignupValidation;

@SpringBootApplication
@ComponentScan("com.saifintex")
@EntityScan(basePackages = {"com.saifintex.entity","com.saifintex.web.entity"})
// we can exclude ErrorMvcAutoConfiguration.class to disable
// the default behaviour of spring boot of error page
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
@Configuration
@PropertySource({"classpath:skypay.properties","classpath:validation.properties"})
@EnableTransactionManagement(proxyTargetClass=true)
@EnableScheduling
public class SkypaytransApplication extends SpringBootServletInitializer implements CommandLineRunner{

	@Value("${server.server.default.port}")
	private int defaultPort;
	@Value("${server.server.redirect.port}")
	private int redirectPort;
	@Value("${server.server.connector.protocol}")
	
	private String tomcatSecurityProtocol;
	@Autowired
	DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(SkypaytransApplication.class, args);
		
	}
	 @Autowired
	 private EntityManagerFactory emf;
	 
	@Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = 
            new JpaTransactionManager();
            tm.setEntityManagerFactory(emf);
            tm.setDataSource(dataSource);
        return tm;
    }
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SkypaytransApplication.class);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("DataSource="+dataSource);
		
	}
	
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
		HibernateJpaSessionFactoryBean fact = new HibernateJpaSessionFactoryBean();
		
		fact.setEntityManagerFactory(emf);
		return fact;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}


	@Bean
	public MessageValidator msgValidatorBean() {
		MessageValidator msgValidator = new MessageValidator();
		return msgValidator;
	}


	@Bean
	public UserLoginValidator uValidator() {
		UserLoginValidator uValid = new UserLoginValidator();
		return uValid;
	}

	@Bean
	public UserSignupValidation usValidator() {
		UserSignupValidation usValid = new UserSignupValidation();
		return usValid;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer PropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Bean
	public SimpleMailMessage mail() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		return mailMessage;
	}

	@Bean
	public JavaMailSenderImpl getBean() {
		return new JavaMailSenderImpl();
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("validation");
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
	
	/*@Bean
	public AuthenticationInterceptor getInterceptorBean() {
		return new AuthenticationInterceptor();
	}*/
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	/*@Bean
	   public LocalContainerEntityManagerFactoryBean
	     entityManagerFactoryBean(){
	      //...
	   }
	 
	   @Bean
	   public PlatformTransactionManager transactionManager(){
	       transactionManager
	        = new JpaTransactionManager();
	      transactionManager.setEntityManagerFactory(
	        entityManagerFactoryBean().getObject() );
	      return transactionManager;
	   }*/
	
	/*@Bean
	public ErrorPageFilter errorPageFilter() {
	    return new ErrorPageFilter();
	}

	@Bean
	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	    filterRegistrationBean.setFilter(filter);
	    filterRegistrationBean.setEnabled(false);
	    return filterRegistrationBean;
	}*/
	
	@Bean
	public AjaxCallEntryPoint ajaxCallEntryPoint() {
		return new AjaxCallEntryPoint("/admin/sessionout");
	}
	
	/*@Bean
    public EmbeddedServletContainerFactory servletContainer(){
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector(){
        Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(defaultPort);
        connector.setSecure(false);
        connector.setRedirectPort(redirectPort);
        return connector;
    }*/
	//org.apache.coyote.http11.Http11NioProtocol

}

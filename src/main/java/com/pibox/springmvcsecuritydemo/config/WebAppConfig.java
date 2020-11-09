package com.pibox.springmvcsecuritydemo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.pibox.springmvcsecuritydemo")
@PropertySource("classpath:persistence-mysql.properties")
public class WebAppConfig {

	private final Environment env;

	public WebAppConfig(Environment env) {
		this.env = env;
	}

	private final Logger logger = Logger.getLogger(getClass().getName());

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Bean
	public DataSource securityDataSource() {
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		logger.info(">>>>> jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info(">>>>> jdbc.user=" + env.getProperty("jdbc.user"));
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.user"));
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.password"));
		securityDataSource.setInitialPoolSize(
				getInProperty("connection.pool.initialPoolSize")
		);
		securityDataSource.setMinPoolSize(
				getInProperty("connection.pool.minPoolSize")
		);
		securityDataSource.setMaxPoolSize(
				getInProperty("connection.pool.maxPoolSize")
		);
		securityDataSource.setMaxIdleTime(
				getInProperty("connection.pool.maxIdleTime")
		);

		return securityDataSource;
	}

	private int getInProperty(String propName) {
		String propVal = env.getProperty(propName);
		assert propVal != null;
		return Integer.parseInt(propVal);
	}
}

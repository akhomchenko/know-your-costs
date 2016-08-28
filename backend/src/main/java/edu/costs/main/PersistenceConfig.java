package edu.costs.main;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@PropertySource("classpath:/maven.properties")
public class PersistenceConfig {

	@Autowired
	private Environment env;

	@Bean
	public AbstractPlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean bean) {
		return new JpaTransactionManager(bean.getObject());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setPersistenceUnitName("unit");
		bean.setPackagesToScan("edu.costs.data");
		bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		bean.setDataSource(dataSource());
		final Properties props = new Properties();
		props.setProperty("hibernate.dialect", env.getProperty("database.dialect"));
		props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("database.auto"));
		props.setProperty("hibernate.flushMode", "COMMIT");
		props.setProperty("hibernate.show_sql", "false");
		props.setProperty("hibernate.format_sql", "false");
		bean.setJpaProperties(props);
		return bean;
	}

	@Bean
	public DataSource dataSource() {
		String url = env.getProperty("database.url");
		String username = env.getProperty("database.username");
		String password = env.getProperty("database.password");
		return new DriverManagerDataSource(url, username, password);
	}
}
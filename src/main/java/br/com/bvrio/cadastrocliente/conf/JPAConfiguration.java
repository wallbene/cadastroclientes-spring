package br.com.bvrio.cadastrocliente.conf;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			Properties additionalProperties) {

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("br.com.bvrio.cadastrocliente.models");
		factoryBean.setDataSource(dataSource);

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setJpaProperties(additionalProperties);

		return factoryBean;
	}

	@Bean
	@Profile("dev")
	public Properties additionalProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		props.setProperty("hibernate.generate_statistics", "true");
		return props;
	}

	@Bean(destroyMethod="close")
	@Profile("dev")
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setUser("root");
		dataSource.setPassword("");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/cadastrocliente");

		// configuration pool via c3p0
	    dataSource.setMinPoolSize(3);	    
	    dataSource.setMaxPoolSize(20);
	    dataSource.setMaxStatements(50); 
	    dataSource.setIdleConnectionTestPeriod(300);//1s, a cada 50 min testa as conexões ociosas
	    dataSource.setMaxIdleTimeExcessConnections(240);
		 
		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	// hibernate statistics

	@Bean
	public Statistics statistics(EntityManagerFactory emf) {
		SessionFactory factory = emf.unwrap(SessionFactory.class);
		return factory.getStatistics();

	}

}
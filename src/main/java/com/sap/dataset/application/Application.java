package com.sap.dataset.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.sap.dataset.dao.PublicDatasetDAO;

@SpringBootApplication
@ComponentScan("com.sap.dataset")
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	@Autowired
	@Qualifier("publicDatasetDAOImpl")
	private PublicDatasetDAO publicDatasetDAO;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {

		//publicDatasetDAO.loadDataProviderCache();
	}
}

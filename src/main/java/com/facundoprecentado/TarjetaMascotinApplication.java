package com.facundoprecentado;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TarjetaMascotinApplication {

    @Value("${spring.datasource.driverClassName}")
    private String databaseDriverClassName;

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.username}")
	private String databaseUsername;

	@Value("${spring.datasource.password}")
	private String databasePassword;

    @Bean
    public DataSource datasource() {
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(databaseDriverClassName);
        ds.setUrl(datasourceUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);

        return ds;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder bCryptPasswordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

	public static void main(String[] args) {
		SpringApplication.run(TarjetaMascotinApplication.class, args);
	}
}

package com.icg.QuerySwitch;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.icg.QuerySwitch.PathBasedConfigResolver;

@SpringBootApplication
public class QuerySwitchApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuerySwitchApplication.class, args);
	}
	  @Bean
	    @ConditionalOnMissingBean(PathBasedConfigResolver.class)
	    public KeycloakConfigResolver keycloakConfigResolver() {
	    	System.out.println("building pathbasedconfigresolver");
	        return new PathBasedConfigResolver();
	    }

}

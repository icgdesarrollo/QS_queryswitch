package com.icg.QuerySwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("${ICG.security.trustedissuers}")
    String trustedissuers;
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    /* 
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    JwtIssuerReactiveAuthenticationManagerResolver authenticationManagerResolver = 
            new JwtIssuerReactiveAuthenticationManagerResolver(
                    "https://idp.example.org/issuerOne",
                    "https://idp.example.org/issuerTwo");

    http
            .authorizeExchange(exchanges -> exchanges
                    .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                    .authenticationManagerResolver(authenticationManagerResolver)
            );
    return http.build();
}
*/


    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      
       
      logger.info("checking permissions");  
      http.authorizeHttpRequests().
      requestMatchers(HttpMethod.POST,"/valida/queries/v3/**").
      hasAuthority("SCOPE_validacion").anyRequest().permitAll();

      //authenticated().
    //  anyRequest().
     // authenticated();
      
      String[] arrTrustedIssuers=trustedissuers.split(",");
     
      /* 
      JwtIssuerAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver
    ("http://190.190.70.204:8080/auth/realms/QS_AGROGTGC", "http://190.190.70.204:8080/auth/realms/QS_INDLGTGC");
    */

 JwtIssuerAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver
    (arrTrustedIssuers);
    




      http.oauth2Login()
            .and()
            .logout()
            .addLogoutHandler(keycloakLogoutHandler)
            .logoutSuccessUrl("/");
     
       
        http
        .oauth2ResourceServer(oauth2 -> oauth2
            .authenticationManagerResolver(authenticationManagerResolver)
        );
         
        return http.build();
    }
   
}
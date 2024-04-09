package com.legion.accountservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {


//    @Bean
//    public SecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//            .csrf().disable()
//            .authorizeHttpRequests().anyRequest().permitAll()
//            .and()
//            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//            .build();
//    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/account/auth/**").permitAll()
            .requestMatchers("/api/account/admin/**").permitAll()
            .requestMatchers("/api/account/users/**").authenticated()
            .requestMatchers("/api/account/social/**").authenticated()
            .and()
            .oauth2ResourceServer().jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter()).and().and().build();
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt ->
            Optional.ofNullable(jwt.getClaimAsStringList("custom_claims"))
                .stream()
                .flatMap(Collection::stream)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())
        );

        return converter;
    }
}

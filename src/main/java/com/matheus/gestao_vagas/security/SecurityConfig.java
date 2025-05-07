package com.matheus.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityCompanyFilter securityCompanyFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    private static final String[] SWAGGER_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resource/**",
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // desabilitar o spring security
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((auth -> {
                auth.requestMatchers("/company/").permitAll();
                auth.requestMatchers("/candidate/").permitAll();
                auth.requestMatchers("/company/auth").permitAll();
                auth.requestMatchers("/candidate/auth").permitAll();
                auth.requestMatchers(SWAGGER_LIST).permitAll();
                auth.anyRequest().authenticated();
            }))
            .addFilterBefore(this.securityCandidateFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(this.securityCompanyFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

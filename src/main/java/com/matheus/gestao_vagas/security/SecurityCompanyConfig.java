package com.matheus.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityCompanyConfig {

    @Autowired
    private SecurityFilter securityFilter;
    private SecurityCompanyFilter securityCompanyFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // desabilitar o spring security
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((auth -> {
                auth.requestMatchers("/company/").permitAll();
                auth.requestMatchers("/candidate/").permitAll();
                auth.requestMatchers("/auth/company").permitAll();
                auth.requestMatchers("/auth/candidate").permitAll();
                auth.requestMatchers("/company/auth").permitAll();
                auth.anyRequest().authenticated();
            }))
            .addFilterBefore(this.securityCandidateFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

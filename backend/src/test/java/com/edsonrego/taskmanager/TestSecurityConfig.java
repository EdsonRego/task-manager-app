package com.edsonrego.taskmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("test")
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desativa CSRF
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // libera todos os endpoints
                .httpBasic(basic -> basic.disable()) // desativa autenticação HTTP Basic
                .formLogin(form -> form.disable()) // desativa formulário de login
                .logout(logout -> logout.disable()); // desativa logout

        return http.build();
    }
}

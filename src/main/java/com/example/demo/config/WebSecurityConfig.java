package com.example.demo.config;

import com.example.demo.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  {

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers("/api/v1/user/auth/login", "/api/v1/user/add").permitAll()
                                // Public read endpoints (no auth required)
                                .requestMatchers(org.springframework.http.HttpMethod.GET,
                                        "/api/v1/product/findById/**",
                                        "/api/v1/product/getAll",
                                        "/api/v1/category/{id}",
                                        "/api/v1/category/getAll",
                                        "/api/v1/product_img/**",
                                        "/api/v1/order/{id}"
                                ).permitAll()
                                .anyRequest()
                                .authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

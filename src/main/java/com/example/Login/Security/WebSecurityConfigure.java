package com.example.Login.Security;

import com.example.Login.constants.URLs;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfigure {

    private final AuthenticationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    );
                })
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> {
                            authorizationManagerRequestMatcherRegistry
                                    .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                    .requestMatchers("/api/v1/auth/**").permitAll()
                                    .requestMatchers("/hello").permitAll()
                                    .requestMatchers(
                                            "/v2/api-docs",
                                            "/v3/api-docs",
                                            "/v3/api-docs/**",
                                            "/swagger-resources",
                                            "/swagger-resources/**",
                                            "/configuration/ui",
                                            "/configuration/security",
                                            "/swagger-ui/**",
                                            "/webjars/**",
                                            "/swagger-ui.html")
                                    .permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .addFilterBefore(filter, //filter kita
                        UsernamePasswordAuthenticationFilter.class // jenis filter
                )
                .build();
    }
}

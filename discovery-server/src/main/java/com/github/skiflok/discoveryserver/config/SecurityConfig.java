package com.github.skiflok.discoveryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${spring.security.user.name}")
  String username;

  @Value("${spring.security.user.password}")
  String password;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.ignoringRequestMatchers("/eureka/**"));
    return httpSecurity.build();
  }


//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests((requests) -> requests
//            .requestMatchers(
//                "/**",
//                "/zipkin/**")
//            .permitAll()
////            .anyRequest().authenticated()
//        );
////                .formLogin((form) -> form
////                        .loginPage("/login")
////                        .permitAll()
////                )
////                .logout(LogoutConfigurer::permitAll);
//
//    return http.build();
//  }


//  @Bean
//  public UserDetailsService userDetailsService() {
//    UserDetails user =
//        User.withDefaultPasswordEncoder()
//            .username(username)
//            .password(password)
//            .roles("USER")
//            .build();
//
//    return new InMemoryUserDetailsManager(user);
//  }

}

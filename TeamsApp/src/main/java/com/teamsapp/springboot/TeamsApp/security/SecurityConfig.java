package com.teamsapp.springboot.TeamsApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/showDbPlayers").hasAnyRole( "ADMIN")
                .antMatchers("/showPlayers").hasAnyRole( "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/showLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accesDenied")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("player")
                        .password("test123")
                        .roles("PLAYER")
                        .build();
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("test123")
                        .roles("ADMIN")
                        .build();


        return new InMemoryUserDetailsManager(user1,user2);
    }









}

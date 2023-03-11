package com.shruteekatech.eCommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.config.AuditingConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    // for In memory User Details, No Details stored in DB
    /*
             @Bean
             public UserDetailsService userDetailsService(){
                 // create users
                 UserDetails adminUser = User.builder()
                         .username("sanju")
                         .password(passwordEncoder().encode("sanju"))
                         .roles("ADMIN")
                         .build();
                 UserDetails normalUser = User.builder()
                         .username("anku")
                         .password(passwordEncoder().encode("anku"))
                         .roles("NORMAL")
                         .build();
                 return new InMemoryUserDetailsManager(adminUser,normalUser);
             }
    */

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Jwt1AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private Jwt3AuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        /*
        // for formLogin : SecurityFilterChain bean implementation
                    http
                       .authorizeRequests()
                       .anyRequest()
                       .authenticated()
                       .and()
                       .formLogin()
                       .loginPage("login.html")
                       .loginProcessingUrl("/process-url")
                       .defaultSuccessUrl("/dashboard")
                       .failureUrl("error")
                       .and()
                       .logout()
                       .logoutUrl("/do-logout");

               return http.build();
         */


        /*
        // for basic authentication
        http.csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();

         */

        // for JWT Authentication
        http.csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth/loginByJWT")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        // set userDetailsService & passwordEncoder into DaoAuthenticationProvider object
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}

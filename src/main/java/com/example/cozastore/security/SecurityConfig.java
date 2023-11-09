package com.example.cozastore.security;

import com.example.cozastore.filter.JwtFilter;
import com.example.cozastore.provider.CustomAuthenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private CustomAuthenProvider customAuthenProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails admin = User.withUsername("NguyenThanhDat")
//                .password(passwordEncoder.encode("123456"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("NguyenVanA")
//                .password(passwordEncoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    // Định nghĩa lại authentication manager sử dụng custom provider
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable() // disable CSRF as using API
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // không sử dụng session
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll() // Allow without authentication
                .antMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/category").permitAll()
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers("/swagger-ui/**",
                        "/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/springfox-swagger-ui/**").permitAll()
                .anyRequest().authenticated() // All other requests need authentication
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

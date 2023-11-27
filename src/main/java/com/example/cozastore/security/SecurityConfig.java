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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://127.0.0.1:5500"); // Add the origin of your frontend application
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable() // disable CSRF as using API
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // không sử dụng session
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll() // Allow without authentication
                .antMatchers(HttpMethod.GET,"/api/images/**").permitAll()
                .antMatchers(HttpMethod.GET,"/category").permitAll()
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers(HttpMethod.POST, "/product/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/product/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/product/**").permitAll()
                .antMatchers(HttpMethod.GET, "/orders/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/orders/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/orders/**").permitAll()
//                .antMatchers(HttpMethod.PUT, "/orders/**").permitAll()
                .antMatchers(HttpMethod.GET, "/comments/**").permitAll()
                .antMatchers(HttpMethod.POST, "/comments/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/comments/**").permitAll()
//                .antMatchers(HttpMethod.PUT, "/comments/**").permitAll()
                .antMatchers(HttpMethod.GET, "/blogs/**").permitAll()
                .antMatchers(HttpMethod.POST, "/blogs/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/blogs/**").permitAll()
//                .antMatchers(HttpMethod.PUT, "/blogs/**").permitAll()
                .antMatchers(HttpMethod.GET, "/order-details/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/order-details/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/order-details/**").permitAll()
//                .antMatchers(HttpMethod.PUT, "/order-details/**").permitAll()
                .antMatchers(HttpMethod.GET, "/product-details/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/product-details/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/product-details/**").permitAll()
//                .antMatchers(HttpMethod.PUT, "/product-details/**").permitAll()
                .antMatchers(HttpMethod.GET,"/category/**").permitAll()

                .antMatchers("/swagger-ui/**",
                        "/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/springfox-swagger-ui/**").permitAll()

                .anyRequest().authenticated() 
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

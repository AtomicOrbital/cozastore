package com.example.cozastore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder páPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Tạo tài khoản và lưu tài khoản trên RAM
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails admin = User.withUsername("NguyenThanhDat")
                .password(passwordEncoder.encode("123456"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("NguyenVanA")
                .password(passwordEncoder.encode("123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    // Quy dinh cau hinh security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf().disable() // disable tan cong dang cross-site vi dang su dung API
                .authorizeHttpRequests()
                    .antMatchers("/user/**").permitAll() // không cần đăng nhập cũng truy cập được
                    .antMatchers(HttpMethod.POST,"/product").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/product").hasAnyRole("USER","ADMIN")
                    .anyRequest().authenticated() // tất cả các link request còn lại phải chứng thực
                .and()
                .httpBasic()// sử dụng kiểu chứng thực basic authen
                .and()
                .build();
    }
}

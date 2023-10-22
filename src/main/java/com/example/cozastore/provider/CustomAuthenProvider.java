package com.example.cozastore.provider;

import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Kiem tra dang nhap provider");
        // Lấy username và password
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = userRepository.findByEmail(username);
        if (user != null) {
            // nếu username tồn tại trong csdl thì kiểm tra đến password
            if (passwordEncoder.matches(password, user.getPassword())) {
                //Tạo chứng thực theo chuẩn security
                List<GrantedAuthority> roles = new ArrayList<>();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
                roles.add(authority);

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, roles);
                return token;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Khai báo kiểu chứng thực hỗ trợ
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

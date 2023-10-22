package com.example.cozastore.filter;


import com.example.cozastore.utils.JwtHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;

//Filter này dùng để lấy token và giải mã token
@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy giá trị của header là Authorization
        String authen = request.getHeader("Authorization");
        if (authen != null && authen.startsWith("Bearer ")) {
            // Cắt chuỗi từ biến authen để lấy ra được token
            String token = authen.substring(7);
            if (!token.isEmpty()) {
                try {
                    // Giải mã token
                    String data = jwtHelper.validationToken(token);
                    System.out.println("kiemtra " + data);
                    //parse chuỗi danh sách role thành chuỗi
                    Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>() {}.getType();
                    List<SimpleGrantedAuthority> roles = gson.fromJson(data, listType);
//                        List<GrantedAuthority> roles = gson.fromJson(data)
                    //Tạo chứng thực theo chuẩn security
//                        List<GrantedAuthority> roles = new ArrayList<>();
//                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
//                        roles.add(authority);

                    UsernamePasswordAuthenticationToken tokenAuthen =
                            new UsernamePasswordAuthenticationToken("", "", roles);
                    SecurityContext context = SecurityContextHolder.getContext();
                    context.setAuthentication(tokenAuthen);
                    System.out.println("Kiemtra123 " + context);
                } catch (Exception e) {
                    System.out.println("Lỗi giải mã token: " + e.getLocalizedMessage());
                }

            }

        }
        System.out.println("Kiem tra" + authen);

        filterChain.doFilter(request, response);
    }


}

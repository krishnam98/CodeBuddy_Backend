package com.codeBuddy.codeBuddy_Backend.config;

import com.codeBuddy.codeBuddy_Backend.Services.JWTService;
import com.codeBuddy.codeBuddy_Backend.Services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path= request.getServletPath();
        System.out.println(path);

//------------Skip Validation for Public Endpoints--------------------  (That's Deep Bro!)
        if(path.startsWith("/auth") || path.startsWith("/verify")){
            filterChain.doFilter(request,response);
            return;
        }

        String authHeader= request.getHeader("Authorization");
        String token="";
        String username="";
        System.out.println("JWT Filter Intercepted! "+", "+authHeader);

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token= authHeader.substring(7);
            username= jwtService.extractUsername(token);

            System.out.println(token+", "+username);

        }

        if (!username.equals("") && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request,response);


    }


}

package com.jwt.mx.generacionJWT.Filters;

import com.jwt.mx.generacionJWT.jwt.JWTUtils;
import com.jwt.mx.generacionJWT.model.User;
import com.jwt.mx.generacionJWT.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private RegisterService registerService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationToken = request.getHeader("Authorization");

        if(authorizationToken != null && authorizationToken.startsWith("Bearer")){

            String jwt =  authorizationToken.substring(7);

            String username=jwtUtils.extractUsername(jwt);

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                User user = (User) registerService.loadUserByUsername(username);

                if(jwtUtils.validateToken(jwt, user)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }

        }

        filterChain.doFilter(request,response);
    }
}

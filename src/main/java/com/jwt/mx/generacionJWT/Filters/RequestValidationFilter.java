package com.jwt.mx.generacionJWT.Filters;

import com.jwt.mx.generacionJWT.jwt.JWTUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestValidationFilter implements Filter {

    private final JWTUtils jwtUtils;


    public RequestValidationFilter(JWTUtils jwtUtils)
    {
        this.jwtUtils=jwtUtils;
    }




    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain){



    }
}

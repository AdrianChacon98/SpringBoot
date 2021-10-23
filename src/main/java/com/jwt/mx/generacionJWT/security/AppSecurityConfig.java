package com.jwt.mx.generacionJWT.security;

import com.google.common.collect.Lists;
import com.jwt.mx.generacionJWT.Filters.JwtFilterRequest;
import com.jwt.mx.generacionJWT.service.LoginService;
import com.jwt.mx.generacionJWT.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RegisterService registerService;
    private final LoginService loginService;



    public AppSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder,RegisterService reqRegisterService,LoginService loginService)
    {
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.registerService=reqRegisterService;

        this.loginService=loginService;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(cors->{
                    CorsConfigurationSource source = request -> {
                       CorsConfiguration config = new CorsConfiguration();

                       config.setAllowedOrigins(Arrays.asList("http://localhost"));

                       config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));

                       return config;

                    };
                    cors.configurationSource(source);

                })
                .addFilterBefore(new JwtFilterRequest(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/register/newUser").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/login/access").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    {
        auth.authenticationProvider(daoAuthenticationProvider());
    }



    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(bCryptPasswordEncoder);

        provider.setUserDetailsService(registerService);

        provider.setUserDetailsService(loginService);

        return provider;
    }



}

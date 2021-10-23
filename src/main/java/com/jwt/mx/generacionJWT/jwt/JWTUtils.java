package com.jwt.mx.generacionJWT.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.mx.generacionJWT.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import javax.crypto.spec.SecretKeySpec;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.tokenTimeMs}")
    private String timeMS;

    private final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);



    public String generateJWT(User user){
        JwtBuilder builder = null;

        try{

            //set the algoritm of hash security
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            //time token created
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //get the secret  key bites parse as a 64 binary method
            byte [] apiKeySecret = DatatypeConverter.parseBase64Binary(secretKey);

            Key signingKey = new SecretKeySpec(apiKeySecret,signatureAlgorithm.getJcaName());


            List<GrantedAuthority> grantedAuthorities = user.getAuthority()
                    .stream()
                    .map(item->new SimpleGrantedAuthority(item.getAuthority()))
                    .collect(Collectors.toList());


            ObjectMapper mapper = new ObjectMapper();

            Map<String,Object> claims = new HashMap<>();
            claims.put("Id",user.getId().toString());
            claims.put("Username",user.getUsername().toString());
            claims.put("Role",user.getRole().getRole().toString());
            claims.put("Authorities", grantedAuthorities.stream().map(item->item.getAuthority())
            .collect(Collectors.toList()));




            //creating a token and claims
            builder = Jwts.builder()
                    .setSubject(user.getUsername().toString())
                    .setIssuedAt(now)
                    .setClaims(claims)
                    .signWith(signatureAlgorithm,signingKey);



            if(!timeMS.isEmpty())
            {

                //the token expires time timeMS is the millsec of 30 minutes

                long expMillis = nowMillis + Long.parseLong(timeMS);
                Date exp = new Date(expMillis);

                //set the time to the token.
                builder.setExpiration(exp);

            }



        }catch (Exception e){

            LOGGER.info("Mensaje de error--->"+e.getMessage());
        }

        //return the jwt
        return builder.compact();
    }





    private Claims getClaims(String token){
         return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                 .parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }



    public boolean isTokenExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String token, User user)
    {
        return user.getUsername().equals(extractUsername(token))  && !isTokenExpired(token);
    }


    /*
    //get the value of token
    public String getValue(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }

    //get the key of the token
    public String getKey(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).getBody();

        return claims.getId();
    }*/






}

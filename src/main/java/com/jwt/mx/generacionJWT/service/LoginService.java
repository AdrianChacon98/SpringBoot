package com.jwt.mx.generacionJWT.service;


import com.jwt.mx.generacionJWT.Email.EmailServiceImp;
import com.jwt.mx.generacionJWT.Repository.UserRepository;
import com.jwt.mx.generacionJWT.jwt.JWTUtils;
import com.jwt.mx.generacionJWT.model.User;
import com.jwt.mx.generacionJWT.model.UserModel.LoginUser;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginService implements UserDetailsService {


    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class.getName());
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired(required = true)
    private UserRepository userRepository;


    private final String USER_NOT_FOUND="User not found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private EmailServiceImp emailServiceImp;

    public LoginService(BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }


    public String signup(LoginUser user)throws Exception
    {

        //check if the email is valid
        boolean isEmail = isValid(user.getEmail());
        if(isEmail) {

            //change password with argon2
            //Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);



            //find the user with the especific email and password
            User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(()->new UsernameNotFoundException(USER_NOT_FOUND));



            boolean isThePassword = comparePassword(user1.getPassword(),user.getPassword());



            String password="hihis";


            if(user1!=null){



                if(user1.getEmail().equals(user.getEmail()) && isThePassword ){


                    //generate jwt
                    String jwt = jwtUtils.generateJWT(user1);
                    //send email to alert somebody access to you account
                    LOGGER.info("Token->"+jwt);
                    return jwt;

                }else{
                    return "Email or password are incorrect";
                }


            }


        }else{
            return "El email no es correcto";
        }

        return "No se encontro usuario";

    }

    public boolean isValid(String email){

        Pattern pattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$");

        Matcher matcher = pattern.matcher(email);



        if(matcher.find())
            return true;
        else{
            return false;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Async
    private boolean comparePassword(String user1Password,String password){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        return argon2.verify(user1Password,password);
    }
}

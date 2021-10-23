package com.jwt.mx.generacionJWT.controller;


import com.jwt.mx.generacionJWT.model.UserModel.LoginUser;
import com.jwt.mx.generacionJWT.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private LoginService loginService;



    @PostMapping(path="/access")
    public ResponseEntity<String> login(@RequestBody LoginUser loginUser)
    {

        try{

            String response = loginService.signup(loginUser);


            return new ResponseEntity<String>(response,HttpStatus.OK);


        }catch(Exception e){
            return new ResponseEntity<String>("Error",HttpStatus.OK);
        }
    }


}

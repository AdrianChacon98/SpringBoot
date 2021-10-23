package com.jwt.mx.generacionJWT.controller;


import com.jwt.mx.generacionJWT.model.UserModel.UserModel;
import com.jwt.mx.generacionJWT.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;


    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> register(@RequestBody UserModel userModel)
    {
        try{
            String response = registerService.register(userModel);

            if(response.equals("El correo no es correcto"))
            {
                return new ResponseEntity<String>(response, HttpStatus.OK);
            }else if(response.equals("El usuario ya existe")){

                return new ResponseEntity<String>(response, HttpStatus.OK);
            }else
            {
                return new ResponseEntity<String>(response,HttpStatus.CREATED);
            }


        }catch(Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }




}

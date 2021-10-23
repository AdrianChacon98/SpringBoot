package com.jwt.mx.generacionJWT.model.UserModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginUser {
    private String email;
    private String password;
}

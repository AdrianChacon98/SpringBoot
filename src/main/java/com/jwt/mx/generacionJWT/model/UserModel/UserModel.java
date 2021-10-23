package com.jwt.mx.generacionJWT.model.UserModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserModel {

    private String name;
    private String lastname;
    private String email;
    private String password;

}

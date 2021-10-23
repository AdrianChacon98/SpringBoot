package com.jwt.mx.generacionJWT.permisos;

public enum  Authorities {

    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    private final String authoritie;

    Authorities(String authoritie)
    {
        this.authoritie=authoritie;
    }

    public String getAuthoritie()
    {
        return authoritie;
    }



}

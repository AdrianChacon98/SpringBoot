package com.jwt.mx.generacionJWT.permisos;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.jwt.mx.generacionJWT.permisos.Authorities.*;

public enum Role {

    USER(Sets.newHashSet(STUDENT_READ,STUDENT_WRITE)),
    ADMIN(Sets.newHashSet(ADMIN_READ,ADMIN_WRITE));

    private final Set<Authorities> authorities;

    Role(Set<Authorities> authorities)
    {
        this.authorities=authorities;
    }

    public Set<Authorities> getAuthorities()
    {
        return authorities;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities()
    {
        Set<SimpleGrantedAuthority> authorities = getAuthorities().stream()
                .map(authoritie->new SimpleGrantedAuthority(authoritie.getAuthoritie()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorities;
    }

}

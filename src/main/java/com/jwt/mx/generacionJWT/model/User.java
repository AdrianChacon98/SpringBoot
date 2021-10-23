package com.jwt.mx.generacionJWT.model;


import com.jwt.mx.generacionJWT.permisos.Authorities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name="user")
@Setter
@Getter
@AllArgsConstructor
public class User implements UserDetails,Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name",length =255 ,nullable = false)
    private String name;

    @Column(name="lastname" ,length = 255,nullable = false)
    private String lastname;

    @Column(name="email",length = 255,nullable = false)
    private String email;


    private String password;

    @Column(name="locked")
    private boolean locked;
    @Column(name="enable")
    private boolean enabled;

    @Column(name = "datecreated")
    private LocalDateTime timeCreated;



    @OneToOne(mappedBy = "roleUser")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Authority> authority;

    @OneToMany(mappedBy = "userAddress" , fetch = FetchType.EAGER)
    private List<Address> address;

    @OneToMany(mappedBy = "idUser", fetch = FetchType.EAGER)
    private List<Orders> orders;





    public User(String name, String lastname, String email, String password, boolean locked, boolean enabled, LocalDateTime timeCreated){
        this.name=name;
        this.lastname=lastname;
        this.email=email;
        this.password=password;
        this.locked=locked;
        this.enabled=enabled;
        this.timeCreated=timeCreated;


    }

    public User(){}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> grantedAuthorities = this.getAuthorities()
                .stream()
                .map(item->new SimpleGrantedAuthority(item.getAuthority()))
                .collect(Collectors.toList());

        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }



    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

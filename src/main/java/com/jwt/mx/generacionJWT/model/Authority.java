package com.jwt.mx.generacionJWT.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="authoritie")
@Setter
@Getter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "authoritie", length = 25, updatable = true)
    private String authority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_authoritie")
    private User user;


}

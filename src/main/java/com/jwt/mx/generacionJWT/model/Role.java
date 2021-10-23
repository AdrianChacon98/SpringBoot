package com.jwt.mx.generacionJWT.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Setter
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role", length = 10, nullable = false)
    private String role;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_role")
    private User roleUser;

}

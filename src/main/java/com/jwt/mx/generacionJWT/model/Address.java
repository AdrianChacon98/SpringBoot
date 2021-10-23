package com.jwt.mx.generacionJWT.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address")
@Setter
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String street;

    private int number;

    @Column(name="zip_code")
    private int zipCode;

    private String city;

    private String cologne;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_address")
    private User userAddress;


    @OneToMany(mappedBy = "idAddress")
    private List<Orders> orders;



}

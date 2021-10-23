package com.jwt.mx.generacionJWT.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;



    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product idProduct;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_address")
    private Address idAddress;


    private String state;


    private LocalDateTime date;



}

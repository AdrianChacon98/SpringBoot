package com.jwt.mx.generacionJWT.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="product")
@Setter
@Getter
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private double price;

    private int amount;

    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Categoria categoria;

    @OneToMany(mappedBy = "idProduct")
    private List<Orders> orders;







}

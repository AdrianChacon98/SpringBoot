package com.jwt.mx.generacionJWT.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {


    public ResponseEntity<String> createProduct(MultipartFile multipartFile){





        return new ResponseEntity<String>("",HttpStatus.OK);

    }


}

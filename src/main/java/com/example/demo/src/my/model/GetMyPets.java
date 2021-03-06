package com.example.demo.src.my.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetMyPets {
    private String petImgUrl;
    private String petName;
    private String petType;
    private String petBecomeFamilyDay;
}

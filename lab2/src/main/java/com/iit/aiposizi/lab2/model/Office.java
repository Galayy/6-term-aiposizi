package com.iit.aiposizi.lab2.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Office {

    private UUID id;
    private Address address;
    private String companyName;
    private Integer roomsNumber;

}

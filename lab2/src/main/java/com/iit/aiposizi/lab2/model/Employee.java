package com.iit.aiposizi.lab2.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Employee {

    private UUID id;
    private String companyName;
    private String department;
    private String firstName;
    private String lastName;
    private Place place;

}

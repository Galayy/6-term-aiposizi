package com.iit.aiposizi.lab2.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Address {

    private UUID id;
    private String country;
    private String city;
    private String street;
    private Integer number;

    @Override
    public String toString() {
        return country + ", " + city + ", " + street + " str., " + number;
    }

}

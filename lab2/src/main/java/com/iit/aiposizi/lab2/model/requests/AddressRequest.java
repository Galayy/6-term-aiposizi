package com.iit.aiposizi.lab2.model.requests;

import lombok.Data;

@Data
public class AddressRequest {

    private String country;
    private String city;
    private String street;
    private Integer number;

}

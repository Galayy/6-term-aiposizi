package com.iit.aiposizi.lab2.model.request;

import lombok.Data;

@Data
public class PlaceEmployeeRequest {

    private String companyName;
    private Integer roomNumber;
    private Integer placeNumber;

}

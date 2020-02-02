package com.iit.aiposizi.lab2.model.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class PlaceRequest {

    private UUID roomId;
    private String employeeLastName;
    private Integer number;

}

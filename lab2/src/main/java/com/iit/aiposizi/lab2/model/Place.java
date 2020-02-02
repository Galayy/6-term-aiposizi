package com.iit.aiposizi.lab2.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Place {

    private UUID id;
    private UUID roomId;
    private Employee employee;
    private Integer number;

}

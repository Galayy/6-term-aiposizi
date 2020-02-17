package com.iit.aiposizi.lab2.model;

import lombok.Data;

import java.util.UUID;

@Data//TODO: override toString
public class Place {

    private UUID id;
    private Room room;
    private Employee employee;
    private Integer number;

}

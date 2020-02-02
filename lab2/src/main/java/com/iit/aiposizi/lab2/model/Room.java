package com.iit.aiposizi.lab2.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Room {

    private UUID id;
    private UUID officeId;
    private Integer number;

}

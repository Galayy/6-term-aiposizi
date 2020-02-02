package com.iit.aiposizi.lab2.model.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class RoomRequest {

    private UUID officeId;
    private Integer number;

}

package com.iit.aiposizi.lab2.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Place {

    private UUID id;
    private Room room;
    private Employee employee;
    private Integer number;

    @Override
    public String toString() {
        return String.format("%s, r. %s, pl. %s", room.getOffice().getCompanyName(), room.getNumber(), number);
    }

}

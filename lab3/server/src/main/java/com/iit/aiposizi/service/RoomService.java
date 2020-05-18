package com.iit.aiposizi.service;

import com.iit.aiposizi.generated.model.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    List<Room> getAll(List<String> offices);

    Room getById(UUID id);

    Room create(UUID officeId, Integer number);

    void delete(UUID id);

}

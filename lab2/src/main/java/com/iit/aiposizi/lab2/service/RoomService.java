package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    List<Room> getAll();

    List<Room> getAllByOfficeId(UUID id);

    Room getById(UUID id);

    Room create(Room room);

    void delete(UUID id);

}

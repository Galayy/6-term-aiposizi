package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Office;
import com.iit.aiposizi.lab2.model.Room;

import java.util.List;
import java.util.UUID;

public interface OfficeService {

    List<Office> getAll();

    List<Room> getRoomsById(UUID id);

    Office getById(UUID id);

    void create(Office office);

    void update(UUID id, Office office);

    void delete(UUID id);

}

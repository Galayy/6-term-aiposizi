package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Office;
import com.iit.aiposizi.lab2.model.Room;
import com.iit.aiposizi.lab2.model.requests.OfficeRequest;

import java.util.List;
import java.util.UUID;

public interface OfficeService {

    List<Office> getAll();

    List<Room> getRoomsById(UUID id);

    Office getById(UUID id);

    Office create(OfficeRequest request);

    Office update(UUID id, OfficeRequest request);

    void delete(UUID id);

}

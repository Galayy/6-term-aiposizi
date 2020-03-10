package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.model.request.PlaceEmployeeRequest;

import java.util.List;
import java.util.UUID;

public interface PlaceService {

    List<Place> getAll();

    Place getByEmployeeId(UUID id);

    List<Place> getAllByRoomId(UUID id);

    Place getById(UUID id);

    void create(Place place);

    void update(UUID id, Place place);

    void delete(UUID id);

    void setEmployee(UUID id, PlaceEmployeeRequest request);

}

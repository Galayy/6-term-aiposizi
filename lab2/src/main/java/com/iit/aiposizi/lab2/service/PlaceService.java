package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.model.requests.PlaceRequest;

import java.util.List;
import java.util.UUID;

public interface PlaceService {

    List<Place> getAll();

    Place getById(UUID id);

    Place create(PlaceRequest request);

    Place update(UUID id, PlaceRequest request);

    void delete(UUID id);

}

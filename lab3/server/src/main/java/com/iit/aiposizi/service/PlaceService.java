package com.iit.aiposizi.service;

import com.iit.aiposizi.entity.EmployeeEntity;
import com.iit.aiposizi.generated.model.Place;
import com.iit.aiposizi.generated.model.PlaceEmployeeRequest;

import java.util.List;
import java.util.UUID;

public interface PlaceService {

    List<Place> getAll(List<String> offices, List<Integer> rooms);

    Place getById(UUID id);

    Place create(UUID roomId, Integer number);

    void delete(UUID id);

    void removeEmployee(UUID id);

    void place(EmployeeEntity employee, PlaceEmployeeRequest request);

}

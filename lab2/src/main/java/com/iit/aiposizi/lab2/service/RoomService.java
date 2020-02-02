package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.Room;
import com.iit.aiposizi.lab2.model.requests.RoomRequest;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    List<Room> getAll();

    List<Employee> getEmployeesById(UUID id);

    Room getById(UUID id);

    Room create(RoomRequest request);

    void delete(UUID id);

}

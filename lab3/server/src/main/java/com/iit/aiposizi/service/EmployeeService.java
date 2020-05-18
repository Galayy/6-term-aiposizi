package com.iit.aiposizi.service;

import com.iit.aiposizi.generated.model.Employee;
import com.iit.aiposizi.generated.model.EmployeeRequest;
import com.iit.aiposizi.generated.model.PlaceEmployeeRequest;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> getAll(List<String> offices, List<Integer> rooms);

    Employee getById(UUID id);

    Employee create(EmployeeRequest employee);

    Employee update(UUID id, EmployeeRequest employee);

    void delete(UUID id);

    void place(UUID id, PlaceEmployeeRequest request);

}

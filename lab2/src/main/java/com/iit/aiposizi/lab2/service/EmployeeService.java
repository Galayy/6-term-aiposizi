package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.requests.EmployeeRequest;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getById(UUID id);

    Employee getByLastName(String employeeLastName);

    Employee create(EmployeeRequest request);

    Employee update(UUID id, EmployeeRequest request);

    void delete(UUID id);

}

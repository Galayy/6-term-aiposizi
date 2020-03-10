package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getById(UUID id);

    Employee getByLastName(String employeeLastName);

    void create(Employee employee);

    void update(UUID id, Employee employee);

    void delete(UUID id);

}

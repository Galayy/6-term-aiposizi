package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.entity.EmployeeEntity;
import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.exception.InvalidInputDataException;
import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.repository.EmployeeRepository;
import com.iit.aiposizi.lab2.service.EmployeeService;
import com.iit.aiposizi.lab2.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.iit.aiposizi.lab2.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service//TODO: setting employees to places
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        var employees = employeeRepository.findAll();
        log.info("{} employees was found", employees.size());
        return employees.stream().map(EMPLOYEE_MAPPER::toModel).collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getById(UUID id) {
        var entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no employee with id %s", id)));
        log.info("Employee with id {} successfully found", id);
        return EMPLOYEE_MAPPER.toModel(entity);
    }

    @Override
    public Employee getByLastName(String employeeLastName) {
        var entity = employeeRepository.findByLastName(employeeLastName)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no employee with last name %s",
                        employeeLastName)));
        log.info("Employee with last name {} successfully found", employeeLastName);
        return EMPLOYEE_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public void create(Employee employee) {
        if (employeeRepository.existsByLastName(employee.getLastName())) {
            throw new InvalidInputDataException(format("Employee with last name %s already exists",
                    employee.getLastName()));
        }
        var entity = EmployeeEntity.builder()
                .speciality(employee.getSpeciality())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .build();
        employeeRepository.save(entity);
        log.info("New employee successfully saved");
    }

    @Override
    @Transactional
    public void update(UUID id, Employee employee) {
        var entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no employee with id %s", id)));
        entity.setSpeciality(employee.getSpeciality());
        entity.setFirstName(employee.getFirstName());
        entity.setLastName(employee.getLastName());
        employeeRepository.save(entity);
        log.info("Employee with id {} successfully updated", id);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException(format("Employee with id %s is not found", id));
        }
        employeeRepository.deleteById(id);
        log.info("Employee with id {} successfully deleted", id);
    }

}

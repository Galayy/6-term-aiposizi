package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.entity.EmployeeEntity;
import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.exception.InvalidInputDataException;
import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.requests.EmployeeRequest;
import com.iit.aiposizi.lab2.repository.EmployeeRepository;
import com.iit.aiposizi.lab2.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.lab2.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        var employees = employeeRepository.findAll();
        log.info("{} employees was found", employees.size());
        return employees.stream().map(EMPLOYEE_MAPPER::toModel).collect(Collectors.toList());
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
    public Employee create(EmployeeRequest request) {
        if (employeeRepository.existsByLastName(request.getLastName())) {
            throw new InvalidInputDataException(format("Employee with last name %s already exists",
                    request.getLastName()));
        }
        var entity = EmployeeEntity.builder()
                .department(request.getDepartment())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        var savedEntity = employeeRepository.save(entity);
        log.info("New employee successfully saved");
        return EMPLOYEE_MAPPER.toModel(savedEntity);
    }

    @Override
    @Transactional
    public Employee update(UUID id, EmployeeRequest request) {
        var entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no employee with id %s", id)));
        entity.setDepartment(request.getDepartment());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        var updatedEntity = employeeRepository.save(entity);
        log.info("Employee with id {} successfully updated", id);
        return EMPLOYEE_MAPPER.toModel(updatedEntity);
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

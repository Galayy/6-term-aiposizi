package com.iit.aiposizi.service.impl;

import com.iit.aiposizi.entity.EmployeeEntity;
import com.iit.aiposizi.exception.EntityNotFoundException;
import com.iit.aiposizi.exception.ForbiddenAccessException;
import com.iit.aiposizi.exception.InvalidInputDataException;
import com.iit.aiposizi.generated.model.Employee;
import com.iit.aiposizi.generated.model.EmployeeRequest;
import com.iit.aiposizi.generated.model.Place;
import com.iit.aiposizi.generated.model.PlaceEmployeeRequest;
import com.iit.aiposizi.repository.EmployeeRepository;
import com.iit.aiposizi.service.EmployeeService;
import com.iit.aiposizi.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.iit.aiposizi.entity.enums.AdminRoleEntity.ADMIN;
import static com.iit.aiposizi.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static com.iit.aiposizi.util.DatabaseUtils.prepareArray;
import static com.iit.aiposizi.util.SecurityUtils.getCurrentUser;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PlaceService placeService;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll(final List<String> offices, final List<Integer> rooms) {
        var preparedEmployeeIds = prepareEmployeeIds(offices, rooms);
        var employees = employeeRepository.findAll(preparedEmployeeIds);
        log.info("{} employees was found", employees.size());
        return employees.stream().map(EMPLOYEE_MAPPER::toModel).collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getById(final UUID id) {
        var entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no employee with id %s", id)));
        log.info("Employee with id {} successfully found", id);
        return EMPLOYEE_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public Employee create(final EmployeeRequest employee) {
        if (employeeRepository.existsByLastName(employee.getLastName())) {
            throw new InvalidInputDataException(format("Employee with last name %s already exists",
                    employee.getLastName()));
        }
        var savedEmployee = employeeRepository.save(EMPLOYEE_MAPPER.toEntity(employee));
        log.info("New employee successfully saved");
        return EMPLOYEE_MAPPER.toModel(savedEmployee);
    }

    @Override
    @Transactional
    public Employee update(final UUID id, final EmployeeRequest employee) {
        var entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no employee with id %s", id)));
        entity.setSpeciality(employee.getSpeciality());
        entity.setFirstName(employee.getFirstName());
        entity.setLastName(employee.getLastName());
        var updatedEmployee = employeeRepository.save(entity);
        log.info("Employee with id {} successfully updated", id);
        return EMPLOYEE_MAPPER.toModel(updatedEmployee);
    }

    @Override
    @Transactional
    public void delete(final UUID id) {
        makeEmptyIfNot(id);
        employeeRepository.deleteById(id);
        log.info("Employee with id {} successfully deleted", id);
    }

    @Override
    @Transactional
    public void place(final UUID id, final PlaceEmployeeRequest request) {
        if (getCurrentUser() == null || getCurrentUser().getRole().equals(ADMIN)) {
            throw new ForbiddenAccessException("Access only for root admins");
        }
        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no employee with id %s", id)));
        placeService.place(employee, request);
    }

    private void makeEmptyIfNot(final UUID id) {
        var employee = employeeRepository.findById(id).orElse(new EmployeeEntity());
        if (employee.getPlace() != null) {
            placeService.removeEmployee(employee.getPlace().getId());
        }
    }

    private String prepareEmployeeIds(final List<String> offices, final List<Integer> rooms) {
        final List<UUID> employeeIds;
        if (offices.equals(emptyList()) && rooms.equals(emptyList())) {
            employeeIds = employeeRepository.findAll().stream().map(EmployeeEntity::getId).collect(toList());
        } else {
            employeeIds = placeService.getAll(offices, rooms).stream().map(Place::getEmployee).filter(Objects::nonNull)
                    .map(Employee::getId).collect(toList());
        }
        return prepareArray(employeeIds);
    }

}

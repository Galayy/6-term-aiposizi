package com.iit.aiposizi.api;

import com.iit.aiposizi.generated.api.EmployeesApi;
import com.iit.aiposizi.generated.model.Employee;
import com.iit.aiposizi.generated.model.EmployeeRequest;
import com.iit.aiposizi.generated.model.PlaceEmployeeRequest;
import com.iit.aiposizi.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@Api(tags = "employees")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmployeesApiImpl implements EmployeesApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false, defaultValue = "") final
                                                          List<String> offices,
                                                          @RequestParam(required = false, defaultValue = "") final
                                                          List<Integer> rooms) {
        var employees = employeeService.getAll(offices, rooms);
        return new ResponseEntity<>(employees, OK);
    }

    @Override
    public ResponseEntity<Void> placeEmployee(@PathVariable final UUID id,
                                              @RequestBody final PlaceEmployeeRequest request) {
        employeeService.place(id, request);
        return new ResponseEntity<>(OK);
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(@PathVariable final UUID id) {
        employeeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(@PathVariable final UUID id,
                                                   @RequestBody final EmployeeRequest employee) {
        var updatedEmployee = employeeService.update(id, employee);
        return new ResponseEntity<>(updatedEmployee, OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody final EmployeeRequest employee) {
        var newEmployee = employeeService.create(employee);
        return new ResponseEntity<>(newEmployee, CREATED);
    }

}

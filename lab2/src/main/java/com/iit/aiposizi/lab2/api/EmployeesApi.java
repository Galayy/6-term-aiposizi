package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.requests.EmployeeRequest;
import com.iit.aiposizi.lab2.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeesApi {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        var employees = employeeService.getAll();
        return new ResponseEntity<>(employees, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        var employee = employeeService.getById(id);
        return new ResponseEntity<>(employee, OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest request) {
        var employee = employeeService.create(request);
        return new ResponseEntity<>(employee, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id, @RequestBody EmployeeRequest request) {
        var updatedEmployee = employeeService.update(id, request);
        return new ResponseEntity<>(updatedEmployee, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable UUID id) {
        employeeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

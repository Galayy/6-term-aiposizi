package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeesController {

    private static final String REDIRECT_ALL_EMPLOYEES_PATH = "redirect:/employees/all";//TODO: refactor here

    private final EmployeeService employeeService;

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("employee", new Address());
        return "create-employee";
    }

    @GetMapping("/all")
    public String getAllPage(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "employees";
    }

    @GetMapping("/{id}/update")//TODO: front logging
    public String updatePage(@PathVariable UUID id, Model model) {
        var employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
        return "create-employee";
    }

    @GetMapping("/{id}/delete")
    public String deleteRoute(@PathVariable UUID id) {
        employeeService.delete(id);
        return REDIRECT_ALL_EMPLOYEES_PATH;
    }

    @PostMapping("/add")//TODO: check if not number
    public String create(@RequestBody Employee employee) {
        employeeService.create(employee);
        return REDIRECT_ALL_EMPLOYEES_PATH;
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable UUID id, @RequestBody Employee employee) {
        employeeService.update(id, employee);
        return REDIRECT_ALL_EMPLOYEES_PATH;
    }

}

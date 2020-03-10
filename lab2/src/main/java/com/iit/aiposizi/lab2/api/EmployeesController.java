package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.request.PlaceEmployeeRequest;
import com.iit.aiposizi.lab2.service.EmployeeService;
import com.iit.aiposizi.lab2.service.OfficeService;
import com.iit.aiposizi.lab2.service.PlaceService;
import com.iit.aiposizi.lab2.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeesController {

    private static final String REDIRECT_ALL_EMPLOYEES_PATH = "redirect:/employees/all";

    private final EmployeeService employeeService;
    private final OfficeService officeService;
    private final PlaceService placeService;
    private final RoomService roomService;

    @GetMapping("/all")
    public String getAllPage(Model model) {
        model.addAttribute("places", placeService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        return "employees";
    }

    @GetMapping("/create")
    public String createEmployeePage(Model model) {
        var employee = new Employee();
        model.addAttribute("employee", employee);
        return "create-employee";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable UUID id, Model model) {
        var employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
        return "create-employee";
    }

    @GetMapping("/{id}/place")
    public String placePage(@PathVariable UUID id, Model model) {
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("offices", officeService.getAll());
        model.addAttribute("places", placeService.getAll());
        model.addAttribute("rooms", roomService.getAll());
        model.addAttribute("place", new PlaceEmployeeRequest());
        return "place-employee";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable UUID id) {
        employeeService.delete(id);
        return REDIRECT_ALL_EMPLOYEES_PATH;
    }

    @PostMapping("/{id}/place")
    public String place(@PathVariable UUID id, PlaceEmployeeRequest placeEmployeeRequest) {
        placeService.setEmployee(id, placeEmployeeRequest);
        var place = placeService.getByEmployeeId(id);
        return format("redirect:/rooms/%s/places", place.getRoom().getId());
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable UUID id, Employee employee) {
        employeeService.update(id, employee);
        return REDIRECT_ALL_EMPLOYEES_PATH;
    }

    @PostMapping("/create")
    public String create(Employee employee) {
        employeeService.create(employee);
        return REDIRECT_ALL_EMPLOYEES_PATH;
    }

}

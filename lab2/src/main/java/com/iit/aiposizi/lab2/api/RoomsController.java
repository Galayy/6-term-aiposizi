package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.service.EmployeeService;
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

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomsController {

    private static final String REDIRECT_HOME_PATH = "redirect:/";

    private final EmployeeService employeeService;
    private final PlaceService placeService;
    private final RoomService roomService;

    @GetMapping("/{id}/places")
    public String getPlacesByRoomIdPage(@PathVariable UUID id, Model model) {
        model.addAttribute("places", placeService.getAllByRoomId(id));
        return "places";
    }

    @GetMapping("/{id}/employees")
    public String getEmployeesByRoomIdPage(@PathVariable UUID id, Model model) {
        model.addAttribute("employees", placeService.getAllByRoomId(id).stream().map(Place::getEmployee)
                .collect(toList()));
        return "employees";
    }

    @GetMapping("/{id}/create-place")
    public String createPlacePage(@PathVariable UUID id, Model model) {
        var room = roomService.getById(id);
        var place = new Place();
        place.setRoom(room);
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("place", place);
        return "create-place";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable UUID id) {
        placeService.delete(id);
        return REDIRECT_HOME_PATH;
    }

    @PostMapping("/{id}/create-place")
    public String createPlace(@PathVariable("id") UUID roomId, Place place) {
        var room = roomService.getById(roomId);
        place.setRoom(room);
        placeService.create(place);
        return "redirect:/rooms/{id}/places";
    }

}

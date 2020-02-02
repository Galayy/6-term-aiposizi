package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.Room;
import com.iit.aiposizi.lab2.model.requests.RoomRequest;
import com.iit.aiposizi.lab2.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomsApi {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        var rooms = roomService.getAll();
        return new ResponseEntity<>(rooms, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable UUID id) {
        var room = roomService.getById(id);
        return new ResponseEntity<>(room, OK);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getRoomEmployeesById(@PathVariable UUID id) {
        var employees = roomService.getEmployeesById(id);
        return new ResponseEntity<>(employees, OK);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomRequest request) {
        var room = roomService.create(request);
        return new ResponseEntity<>(room, CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoom(@PathVariable UUID id) {
        roomService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

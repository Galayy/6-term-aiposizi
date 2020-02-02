package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Office;
import com.iit.aiposizi.lab2.model.Room;
import com.iit.aiposizi.lab2.model.requests.OfficeRequest;
import com.iit.aiposizi.lab2.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offices")
public class OfficesApi {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<List<Office>> getOffices() {
        var offices = officeService.getAll();
        return new ResponseEntity<>(offices, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Office> getOfficeById(@PathVariable UUID id) {
        var office = officeService.getById(id);
        return new ResponseEntity<>(office, OK);
    }

    @GetMapping("/{id}/rooms")
    public ResponseEntity<List<Room>> getOfficeRoomsById(@PathVariable UUID id) {
        var rooms = officeService.getRoomsById(id);
        return new ResponseEntity<>(rooms, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Office> updateOffice(@PathVariable UUID id, @RequestBody OfficeRequest request) {
        var updatedOffice = officeService.update(id, request);
        return new ResponseEntity<>(updatedOffice, OK);
    }

    @PostMapping
    public ResponseEntity<Office> createOffice(@RequestBody OfficeRequest request) {
        var office = officeService.create(request);
        return new ResponseEntity<>(office, CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOffice(@PathVariable UUID id) {
        officeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

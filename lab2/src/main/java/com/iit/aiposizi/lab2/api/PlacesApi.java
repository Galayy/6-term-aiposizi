package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.model.requests.PlaceRequest;
import com.iit.aiposizi.lab2.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController//TODO: check employees for uniqueness
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlacesApi {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<Place>> getPlaces() {
        var places = placeService.getAll();
        return new ResponseEntity<>(places, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable UUID id) {
        var place = placeService.getById(id);
        return new ResponseEntity<>(place, OK);
    }

    @PostMapping
    public ResponseEntity<Place> createPlace(@RequestBody PlaceRequest request) {
        var place = placeService.create(request);
        return new ResponseEntity<>(place, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable UUID id, @RequestBody PlaceRequest request) {
        var updatedPlace = placeService.update(id, request);
        return new ResponseEntity<>(updatedPlace, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlace(@PathVariable UUID id) {
        placeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

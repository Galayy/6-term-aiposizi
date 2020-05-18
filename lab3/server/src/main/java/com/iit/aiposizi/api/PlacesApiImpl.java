package com.iit.aiposizi.api;

import com.iit.aiposizi.generated.api.PlacesApi;
import com.iit.aiposizi.generated.model.Place;
import com.iit.aiposizi.service.PlaceService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@Api(tags = "places")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlacesApiImpl implements PlacesApi {

    private final PlaceService placeService;

    @Override
    public ResponseEntity<Place> createPlace(@RequestParam("roomId") final UUID roomId,
                                             @RequestParam("number") final Integer number) {
        var newPlace = placeService.create(roomId, number);
        return new ResponseEntity<>(newPlace, CREATED);
    }

    @Override
    public ResponseEntity<List<Place>> getAllPlaces(@RequestParam(required = false, defaultValue = "") final
                                                    List<String> offices,
                                                    @RequestParam(required = false, defaultValue = "") final
                                                    List<Integer> rooms) {
        var places = placeService.getAll(offices, rooms);
        return new ResponseEntity<>(places, OK);
    }

    @Override
    public ResponseEntity<Place> getPlaceById(@PathVariable final UUID id) {
        var place = placeService.getById(id);
        return new ResponseEntity<>(place, OK);
    }

    @Override
    public ResponseEntity<Void> deletePlace(@PathVariable final UUID id) {
        placeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

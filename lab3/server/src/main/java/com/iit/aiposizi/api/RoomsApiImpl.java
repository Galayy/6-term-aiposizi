package com.iit.aiposizi.api;

import com.iit.aiposizi.generated.api.RoomsApi;
import com.iit.aiposizi.generated.model.Place;
import com.iit.aiposizi.generated.model.Room;
import com.iit.aiposizi.service.PlaceService;
import com.iit.aiposizi.service.RoomService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@Api(tags = "rooms")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoomsApiImpl implements RoomsApi {

    private final RoomService roomService;

    @Override
    public ResponseEntity<Room> createRoom(@RequestParam("officeId") final UUID officeId,
                                           @RequestParam("number") final Integer number) {
        var newRoom = roomService.create(officeId, number);
        return new ResponseEntity<>(newRoom, CREATED);
    }

    @Override
    public ResponseEntity<List<Room>> getAllRooms(@RequestParam(required = false, defaultValue = "") final
                                                  List<String> offices) {
        var rooms = roomService.getAll(offices);
        return new ResponseEntity<>(rooms, OK);
    }

    @Override
    public ResponseEntity<Room> getRoomById(@PathVariable final UUID id) {
        var room = roomService.getById(id);
        return new ResponseEntity<>(room, OK);
    }

    @Override
    public ResponseEntity<Void> deleteRoomById(@PathVariable final UUID id) {
        roomService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

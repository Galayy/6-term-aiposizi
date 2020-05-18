package com.iit.aiposizi.service.impl;

import com.iit.aiposizi.entity.EmployeeEntity;
import com.iit.aiposizi.entity.PlaceEntity;
import com.iit.aiposizi.exception.EntityNotFoundException;
import com.iit.aiposizi.exception.InvalidInputDataException;
import com.iit.aiposizi.generated.model.Place;
import com.iit.aiposizi.generated.model.PlaceEmployeeRequest;
import com.iit.aiposizi.repository.PlaceRepository;
import com.iit.aiposizi.service.OfficeService;
import com.iit.aiposizi.service.PlaceService;
import com.iit.aiposizi.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.mapper.PlaceMapper.PLACE_MAPPER;
import static com.iit.aiposizi.mapper.RoomMapper.ROOM_MAPPER;
import static com.iit.aiposizi.util.DatabaseUtils.prepareArray;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    private final OfficeService officeService;
    private final RoomService roomService;

    @Override
    @Transactional(readOnly = true)
    public List<Place> getAll(final List<String> offices, final List<Integer> rooms) {
        var places = placeRepository.findAll(prepareArray(offices), prepareArray(rooms));
        log.info("{} places was found", places.size());
        return places.stream().map(PLACE_MAPPER::toModel).collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Place getById(final UUID id) {
        var place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", id)));
        log.info("Place with id {} successfully found", id);
        return PLACE_MAPPER.toModel(place);
    }

    @Override
    @Transactional
    public Place create(final UUID roomId, final Integer number) {
        if (placeRepository.existsByRoomIdAndNumber(roomId, number)) {
            throw new InvalidInputDataException("Such place already exists");
        }
        var room = roomService.getById(roomId);
        var office = officeService.retrieveById(room.getOffice().getId());

        var entity = PlaceEntity.builder()
                .number(number)
                .room(ROOM_MAPPER.toEntity(room, office))
                .build();
        var savedPlace = placeRepository.save(entity);
        log.info("New place successfully saved");
        return PLACE_MAPPER.toModel(savedPlace);
    }

    @Override
    @Transactional
    public void delete(final UUID id) {
        removeEmployee(id);
        placeRepository.deleteById(id);
        log.info("Place with id {} successfully deleted", id);
    }

    @Override
    @Transactional
    public void removeEmployee(final UUID id) {
        var place = placeRepository.findById(id).orElse(new PlaceEntity());
        if (place.getEmployee() != null) {
            place.setEmployee(null);
            placeRepository.saveAndFlush(place);
        }
    }

    @Override
    public void place(final EmployeeEntity employee, final PlaceEmployeeRequest request) {
        var place = placeRepository.findAll(prepareArray(request.getCompanyName()),
                prepareArray(request.getRoomNumber())).stream()
                .filter(requiredPlace -> requiredPlace.getNumber().equals(request.getPlaceNumber())).findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException(format("There is no place with number %s in room %s in company %s",
                                request.getPlaceNumber(), request.getRoomNumber(), request.getCompanyName())));

        if (place.getEmployee() == null) {
            place.setEmployee(employee);
            placeRepository.saveAndFlush(place);
            log.info("Employee could be found now on place with number {} in room {} in company {}",
                    request.getPlaceNumber(), request.getRoomNumber(), request.getCompanyName());
        } else {
            throw new InvalidInputDataException(format("Place is already taken by employee with id %s",
                    place.getEmployee().getId()));
        }
    }

}

package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.entity.PlaceEntity;
import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.exception.InvalidInputDataException;
import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.model.request.PlaceEmployeeRequest;
import com.iit.aiposizi.lab2.repository.PlaceRepository;
import com.iit.aiposizi.lab2.service.EmployeeService;
import com.iit.aiposizi.lab2.service.PlaceService;
import com.iit.aiposizi.lab2.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.lab2.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static com.iit.aiposizi.lab2.mapper.PlaceMapper.PLACE_MAPPER;
import static com.iit.aiposizi.lab2.mapper.RoomMapper.ROOM_MAPPER;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    private final EmployeeService employeeService;
    private final RoomService roomService;

    @Override
    @Transactional(readOnly = true)
    public List<Place> getAll() {
        var places = placeRepository.findAll();
        log.info("{} places was found", places.size());
        return places.stream().map(PLACE_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Place getByEmployeeId(UUID employeeId) {
        return placeRepository.findByEmployeeId(employeeId)
                .map(place -> {
                    log.info("Place with employee {} was found", employeeId);
                    return PLACE_MAPPER.toModel(place);
                })
                .orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", employeeId)));
    }

    @Override
    public List<Place> getAllByRoomId(UUID id) {
        var places = placeRepository.findAllByRoomId(id);
        log.info("{} places was found", places.size());
        return places.stream().map(PLACE_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Place getById(UUID id) {
        var place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", id)));
        log.info("Place with id {} successfully found", id);
        return PLACE_MAPPER.toModel(place);
    }

    @Override
    @Transactional
    public void create(Place place) {
        if (placeRepository.existsByRoomIdAndNumber(place.getRoom().getId(), place.getNumber())) {
            throw new InvalidInputDataException("Such place already exists");
        }
        var room = roomService.getById(place.getRoom().getId());

        var entity = PlaceEntity.builder()
                .number(place.getNumber())
                .room(ROOM_MAPPER.toEntity(room))
                .build();
        placeRepository.save(entity);
        log.info("New place successfully saved");
    }

    @Override
    @Transactional
    public void update(UUID id, Place place) {
        placeRepository.findById(id).map(entity -> {
            if (entity.getEmployee() != null) {
                entity.setEmployee(null);
                var updatedEntity = placeRepository.save(entity);
                log.info("Employee from place with id {} successfully deleted", id);
                return PLACE_MAPPER.toModel(updatedEntity);
            } else {
                throw new InvalidInputDataException(format("Place with id %s is already empty", id));
            }
        }).orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", id)));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        makeEmptyIfNot(id);
        placeRepository.deleteById(id);
        log.info("Place with id {} successfully deleted", id);
    }

    @Override
    public void setEmployee(UUID id, PlaceEmployeeRequest request) {
        var employee = employeeService.getById(id);
        placeRepository.findByCompanyNameAndRoomNumberAndNumber(request.getCompanyName(),
                request.getRoomNumber(), request.getPlaceNumber()).map(entity -> {
            if (entity.getEmployee() != null) {
                throw new InvalidInputDataException(format("Place %s in room %s of company %s is not free",
                        request.getPlaceNumber(), request.getRoomNumber(), request.getCompanyName()));
            }
            entity.setEmployee(EMPLOYEE_MAPPER.toEntity(employee));
            return placeRepository.saveAndFlush(entity);
        }).orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", id)));
    }

    private void makeEmptyIfNot(final UUID id) {
        var place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", id)));
        if (placeRepository.existsById(id) && place.getEmployee() != null) {
            place.setEmployee(null);
            placeRepository.save(place);
        }
    }

}

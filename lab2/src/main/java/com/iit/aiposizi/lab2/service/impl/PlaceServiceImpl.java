package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.entity.PlaceEntity;
import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.exception.InvalidInputDataException;
import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.model.requests.PlaceRequest;
import com.iit.aiposizi.lab2.repository.PlaceRepository;
import com.iit.aiposizi.lab2.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.lab2.mapper.AddressMapper.ADDRESS_MAPPER;
import static com.iit.aiposizi.lab2.mapper.EmployeeMapper.EMPLOYEE_MAPPER;
import static com.iit.aiposizi.lab2.mapper.OfficeMapper.OFFICE_MAPPER;
import static com.iit.aiposizi.lab2.mapper.PlaceMapper.PLACE_MAPPER;
import static com.iit.aiposizi.lab2.mapper.RoomMapper.ROOM_MAPPER;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final OfficeService officeService;
    private final RoomService roomService;

    @Override
    @Transactional(readOnly = true)
    public List<Place> getAll() {
        var places = placeRepository.findAll();
        log.info("{} places was found", places.size());
        return places.stream().map(place -> PLACE_MAPPER.toModel(place, EMPLOYEE_MAPPER.toModel(place.getEmployee())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Place getById(UUID id) {
        var place = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", id)));
        return PLACE_MAPPER.toModel(place, EMPLOYEE_MAPPER.toModel(place.getEmployee()));
    }

    @Override
    @Transactional
    public Place create(PlaceRequest request) {
        if (placeRepository.existsByEmployee_LastNameAndRoom_IdAndNumber(request.getEmployeeLastName(),
                request.getRoomId(), request.getNumber())) {
            throw new InvalidInputDataException("Such place already exists");
        }
        var employee = employeeService.getByLastName(request.getEmployeeLastName());
        var room = roomService.getById(request.getRoomId());
        var office = officeService.getById(room.getOfficeId());
        var address = addressService.getById(office.getAddressId());

        var entity = PlaceEntity.builder()
                .number(request.getNumber())
                .employee(EMPLOYEE_MAPPER.toEntity(employee))
                .room(ROOM_MAPPER.toEntity(room, OFFICE_MAPPER.toEntity(office, ADDRESS_MAPPER.toEntity(address))))
                .build();
        var savedEntity = placeRepository.save(entity);
        log.info("New place successfully saved");
        return PLACE_MAPPER.toModel(savedEntity, employee);
    }

    @Override
    @Transactional
    public Place update(UUID id, PlaceRequest request) {
        var entity = placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no place with id %s", id)));
        var employee = employeeService.getByLastName(request.getEmployeeLastName());
        var room = roomService.getById(request.getRoomId());
        var office = officeService.getById(room.getOfficeId());
        var address = addressService.getById(office.getAddressId());

        entity.setNumber(request.getNumber());
        entity.setEmployee(EMPLOYEE_MAPPER.toEntity(employee));
        entity.setRoom(ROOM_MAPPER.toEntity(room, OFFICE_MAPPER.toEntity(office, ADDRESS_MAPPER.toEntity(address))));
        var updatedEntity = placeRepository.save(entity);
        log.info("Place with id {} successfully updated", id);
        return PLACE_MAPPER.toModel(updatedEntity, employee);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!placeRepository.existsById(id)) {
            throw new EntityNotFoundException(format("Place with id %s is not found", id));
        }
        placeRepository.deleteById(id);
        log.info("Place with id {} successfully deleted", id);
    }

}

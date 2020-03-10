package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.entity.RoomEntity;
import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.exception.InvalidInputDataException;
import com.iit.aiposizi.lab2.model.Room;
import com.iit.aiposizi.lab2.repository.RoomRepository;
import com.iit.aiposizi.lab2.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.lab2.mapper.OfficeMapper.OFFICE_MAPPER;
import static com.iit.aiposizi.lab2.mapper.RoomMapper.ROOM_MAPPER;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAll() {
        var rooms = roomRepository.findAll();
        log.info("{} rooms was found", rooms.size());
        return rooms.stream().map(ROOM_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAllByOfficeId(UUID id) {
        var rooms = roomRepository.findAllByOfficeId(id);
        log.info("{} rooms was found", rooms.size());
        return rooms.stream().map(ROOM_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Room getById(UUID id) {
        var entity = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no room with id %s", id)));
        log.info("Room with id {} successfully found", id);
        return ROOM_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public Room create(Room room) {
        if (roomRepository.existsByOfficeIdAndNumber(room.getOffice().getId(), room.getNumber())) {
            throw new InvalidInputDataException("Such room already exists");
        }

        var entity = RoomEntity.builder()
                .office(OFFICE_MAPPER.toEntity(room.getOffice()))
                .number(room.getNumber())
                .build();
        var savedEntity = roomRepository.save(entity);
        log.info("New room successfully saved");
        return ROOM_MAPPER.toModel(savedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException(format("Room with id %s is not found", id));
        }
        roomRepository.deleteById(id);
        log.info("Room with id {} successfully deleted", id);
    }

}

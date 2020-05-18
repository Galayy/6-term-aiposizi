package com.iit.aiposizi.service.impl;

import com.iit.aiposizi.entity.RoomEntity;
import com.iit.aiposizi.exception.EntityNotFoundException;
import com.iit.aiposizi.exception.InvalidInputDataException;
import com.iit.aiposizi.generated.model.Room;
import com.iit.aiposizi.repository.RoomRepository;
import com.iit.aiposizi.service.OfficeService;
import com.iit.aiposizi.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.mapper.RoomMapper.ROOM_MAPPER;
import static com.iit.aiposizi.util.DatabaseUtils.prepareArray;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final OfficeService officeService;

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAll(final List<String> offices) {
        var rooms = roomRepository.findAll(prepareArray(offices));
        log.info("{} rooms was found", rooms.size());
        return rooms.stream().map(ROOM_MAPPER::toModel).collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Room getById(final UUID id) {
        var entity = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no room with id %s", id)));
        log.info("Room with id {} successfully found", id);
        return ROOM_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public Room create(final UUID officeId, final Integer number) {
        if (roomRepository.existsByOfficeIdAndNumber(officeId, number)) {
            throw new InvalidInputDataException("Such room already exists");
        }
        var office = officeService.retrieveById(officeId);

        var entity = RoomEntity.builder()
                .office(office)
                .number(number)
                .build();
        var savedEntity = roomRepository.save(entity);
        log.info("New room successfully saved");
        return ROOM_MAPPER.toModel(savedEntity);
    }

    @Override
    @Transactional
    public void delete(final UUID id) {
        roomRepository.deleteById(id);
        log.info("Room with id {} successfully deleted", id);
    }

}

package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.entity.OfficeEntity;
import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.model.Office;
import com.iit.aiposizi.lab2.model.Room;
import com.iit.aiposizi.lab2.model.requests.OfficeRequest;
import com.iit.aiposizi.lab2.repository.OfficeRepository;
import com.iit.aiposizi.lab2.service.AddressService;
import com.iit.aiposizi.lab2.service.OfficeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.lab2.mapper.AddressMapper.ADDRESS_MAPPER;
import static com.iit.aiposizi.lab2.mapper.OfficeMapper.OFFICE_MAPPER;
import static com.iit.aiposizi.lab2.mapper.RoomMapper.ROOM_MAPPER;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    private final AddressService addressService;

    @Override
    @Transactional(readOnly = true)
    public List<Office> getAll() {
        var offices = officeRepository.findAll();
        log.info("{} offices was found", offices.size());
        return offices.stream().map(OFFICE_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsById(UUID id) {
        var office = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no office with id %s", id)));
        return office.getRooms().stream().map(ROOM_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Office getById(UUID id) {
        var entity = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no office with id %s", id)));
        log.info("Office with id {} successfully found", id);
        return OFFICE_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public Office create(OfficeRequest request) {
        var address = addressService.getById(request.getAddressId());
        var office = OfficeEntity.builder().address(ADDRESS_MAPPER.toEntity(address)).build();
        var savedOffice = officeRepository.save(office);
        log.info("New office successfully saved");
        return OFFICE_MAPPER.toModel(savedOffice);
    }

    @Override
    @Transactional
    public Office update(UUID id, OfficeRequest request) {
        var entity = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no office with id %s", id)));
        var address = addressService.getById(request.getAddressId());
        entity.setAddress(ADDRESS_MAPPER.toEntity(address));
        var updatedEntity = officeRepository.save(entity);
        log.info("Office with id {} successfully updated", id);
        return OFFICE_MAPPER.toModel(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!officeRepository.existsById(id)) {
            throw new EntityNotFoundException(format("Office with id %s is not found", id));
        }
        officeRepository.deleteById(id);
        log.info("Office with id {} successfully deleted", id);
    }

}

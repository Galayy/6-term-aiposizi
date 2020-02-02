package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.entity.AddressEntity;
import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.exception.InvalidInputDataException;
import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.model.requests.AddressRequest;
import com.iit.aiposizi.lab2.repository.AddressRepository;
import com.iit.aiposizi.lab2.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iit.aiposizi.lab2.mapper.AddressMapper.ADDRESS_MAPPER;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Address> getAll() {
        var addresses = addressRepository.findAll();
        log.info("{} addresses was found", addresses.size());
        return addresses.stream().map(ADDRESS_MAPPER::toModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Address getById(UUID id) {
        var entity = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no address with id %s", id)));
        log.info("Address with id {} successfully found", id);
        return ADDRESS_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public Address create(AddressRequest request) {
        if (addressRepository.existsByCountryAndCityAndStreetAndNumber(request.getCountry(), request.getCity(),
                request.getStreet(), request.getNumber())) {
            throw new InvalidInputDataException(format("Address %s already exists", request.toString()));
        }
        var entity = AddressEntity.builder()
                .country(request.getCountry())
                .city(request.getCity())
                .street(request.getStreet())
                .number(request.getNumber())
                .build();
        var savedEntity = addressRepository.save(entity);
        log.info("New address successfully saved");
        return ADDRESS_MAPPER.toModel(savedEntity);
    }

    @Override
    @Transactional
    public Address update(UUID id, AddressRequest request) {
        var entity = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no address with id %s", id)));
        entity.setCountry(request.getCountry());
        entity.setCity(request.getCity());
        entity.setStreet(request.getStreet());
        entity.setNumber(request.getNumber());
        var updatedEntity = addressRepository.save(entity);
        log.info("Address with id {} successfully updated", id);
        return ADDRESS_MAPPER.toModel(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException(format("Address with id %s is not found", id));
        }
        addressRepository.deleteById(id);
        log.info("Address with id {} successfully deleted", id);
    }

}
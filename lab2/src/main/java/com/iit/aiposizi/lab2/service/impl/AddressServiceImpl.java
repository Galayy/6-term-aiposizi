package com.iit.aiposizi.lab2.service.impl;

import com.iit.aiposizi.lab2.exception.EntityNotFoundException;
import com.iit.aiposizi.lab2.model.Address;
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
    public void update(UUID id, Address address) {
        addressRepository.findById(id).map(entity -> {
            entity.setCountry(address.getCountry());
            entity.setCity(address.getCity());
            entity.setStreet(address.getStreet());
            entity.setNumber(address.getNumber());
            addressRepository.save(entity);
            log.info("Address with id {} successfully updated", id);
            return entity;
        }).orElseThrow(() -> new EntityNotFoundException(format("There is no address with id %s", id)));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        addressRepository.deleteById(id);
        log.info("Address with id {} successfully deleted", id);
    }

    @Override
    @Transactional
    public void create(Address address) {
        addressRepository.save(ADDRESS_MAPPER.toEntity(address));
        log.info("New address successfully saved");
    }

}

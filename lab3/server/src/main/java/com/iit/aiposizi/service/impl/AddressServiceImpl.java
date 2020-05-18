package com.iit.aiposizi.service.impl;

import com.iit.aiposizi.entity.AddressEntity;
import com.iit.aiposizi.exception.EntityNotFoundException;
import com.iit.aiposizi.generated.model.Address;
import com.iit.aiposizi.generated.model.AddressRequest;
import com.iit.aiposizi.repository.AddressRepository;
import com.iit.aiposizi.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.iit.aiposizi.mapper.AddressMapper.ADDRESS_MAPPER;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

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
        return addresses.stream().map(ADDRESS_MAPPER::toModel).collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Address getById(final UUID id) {
        var entity = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no address with id %s", id)));
        log.info("Address with id {} successfully found", id);
        return ADDRESS_MAPPER.toModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressEntity retrieveById(UUID id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no address with id %s", id)));
    }

    @Override
    @Transactional
    public Address update(final UUID id, final AddressRequest address) {
        return addressRepository.findById(id).map(entity -> {
            entity.setCountry(address.getCountry());
            entity.setCity(address.getCity());
            entity.setStreet(address.getStreet());
            entity.setNumber(address.getNumber());
            var updatedEntity = addressRepository.save(entity);
            log.info("Address with id {} successfully updated", id);
            return updatedEntity;
        }).map(ADDRESS_MAPPER::toModel)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no address with id %s", id)));
    }

    @Override
    @Transactional
    public void delete(final UUID id) {
        addressRepository.deleteById(id);
        log.info("Address with id {} successfully deleted", id);
    }

    @Override
    @Transactional
    public Address create(final AddressRequest address) {
        var savedAddress = addressRepository.save(ADDRESS_MAPPER.toEntity(address));
        log.info("New address successfully saved");
        return ADDRESS_MAPPER.toModel(savedAddress);
    }

}

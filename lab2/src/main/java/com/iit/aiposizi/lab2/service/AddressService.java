package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Address;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    List<Address> getAll();

    Address getById(UUID id);

    void create(Address address);

    void update(UUID id, Address address);

    void delete(UUID id);

}

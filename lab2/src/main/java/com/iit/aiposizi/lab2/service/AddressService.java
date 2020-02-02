package com.iit.aiposizi.lab2.service;

import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.model.requests.AddressRequest;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    List<Address> getAll();

    Address getById(UUID id);

    Address create(AddressRequest request);

    Address update(UUID id, AddressRequest request);

    void delete(UUID id);

}

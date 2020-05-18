package com.iit.aiposizi.service;

import com.iit.aiposizi.entity.AddressEntity;
import com.iit.aiposizi.generated.model.Address;
import com.iit.aiposizi.generated.model.AddressRequest;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    List<Address> getAll();

    Address getById(UUID id);

    AddressEntity retrieveById(UUID id);

    Address create(AddressRequest address);

    Address update(UUID id, AddressRequest address);

    void delete(UUID id);

}

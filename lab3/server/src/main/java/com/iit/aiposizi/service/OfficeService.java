package com.iit.aiposizi.service;


import com.iit.aiposizi.entity.OfficeEntity;
import com.iit.aiposizi.generated.model.Office;

import java.util.List;
import java.util.UUID;

public interface OfficeService {

    List<Office> getAll();

    Office getById(UUID id);

    Office create(UUID addressId, String companyName);

    OfficeEntity retrieveById(UUID officeId);

    void delete(UUID id);

}

package com.iit.aiposizi.service.impl;

import com.iit.aiposizi.entity.AddressEntity;
import com.iit.aiposizi.entity.OfficeEntity;
import com.iit.aiposizi.exception.EntityNotFoundException;
import com.iit.aiposizi.exception.InvalidInputDataException;
import com.iit.aiposizi.generated.model.Office;
import com.iit.aiposizi.repository.OfficeRepository;
import com.iit.aiposizi.service.AddressService;
import com.iit.aiposizi.service.OfficeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.iit.aiposizi.mapper.OfficeMapper.OFFICE_MAPPER;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

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
        return offices.stream().map(OFFICE_MAPPER::toModel).collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Office getById(final UUID id) {
        var entity = officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no office with id %s", id)));
        log.info("Office with id {} successfully found", id);
        return OFFICE_MAPPER.toModel(entity);
    }

    @Override
    @Transactional
    public Office create(final UUID addressId, final String companyName) {
        var address = addressService.retrieveById(addressId);
        throwIfHasOffice(address);
        var newOffice = OfficeEntity.builder()
                .address(address)
                .companyName(companyName)
                .build();
        var savedOffice = officeRepository.save(newOffice);
        log.info("New office successfully saved");
        return OFFICE_MAPPER.toModel(savedOffice);
    }

    private void throwIfHasOffice(final AddressEntity address) {
        if (address.getOffice() != null) {
            throw new InvalidInputDataException(format("There is already office on address with id %s",
                    address.getId()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeEntity retrieveById(final UUID officeId) {
        return officeRepository.findById(officeId)
                .orElseThrow(() -> new EntityNotFoundException(format("There is no office with id %s", officeId)));
    }

    @Override
    @Transactional
    public void delete(final UUID id) {
        officeRepository.deleteById(id);
        log.info("Office with id {} successfully deleted", id);
    }

}

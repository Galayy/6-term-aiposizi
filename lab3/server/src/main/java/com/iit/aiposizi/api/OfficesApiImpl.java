package com.iit.aiposizi.api;

import com.iit.aiposizi.generated.api.OfficesApi;
import com.iit.aiposizi.generated.model.Office;
import com.iit.aiposizi.service.OfficeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@Api(tags = "offices")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OfficesApiImpl implements OfficesApi {

    private final OfficeService officeService;

    @Override
    public ResponseEntity<Office> createOffice(@RequestParam("addressId") final UUID addressId,
                                               @RequestParam("companyName") final String companyName) {
        var newOffice = officeService.create(addressId, companyName);
        return new ResponseEntity<>(newOffice, CREATED);
    }

    @Override
    public ResponseEntity<List<Office>> getAllOffices() {
        var offices = officeService.getAll();
        return new ResponseEntity<>(offices, OK);
    }

    @Override
    public ResponseEntity<Office> getOfficeById(@PathVariable final UUID id) {
        var office = officeService.getById(id);
        return new ResponseEntity<>(office, OK);
    }

    @Override
    public ResponseEntity<Void> deleteOffice(@PathVariable final UUID id) {
        officeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

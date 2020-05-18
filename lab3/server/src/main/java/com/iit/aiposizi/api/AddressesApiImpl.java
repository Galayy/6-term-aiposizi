package com.iit.aiposizi.api;

import com.iit.aiposizi.generated.api.AddressesApi;
import com.iit.aiposizi.generated.model.Address;
import com.iit.aiposizi.generated.model.AddressRequest;
import com.iit.aiposizi.service.AddressService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@Api(tags = "addresses")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AddressesApiImpl implements AddressesApi {

    private final AddressService addressService;

    @Override
    public ResponseEntity<List<Address>> getAllAddresses() {
        var addresses = addressService.getAll();
        return new ResponseEntity<>(addresses, OK);
    }

    @Override
    public ResponseEntity<Address> getAddressById(@PathVariable final UUID id) {
        var address = addressService.getById(id);
        return new ResponseEntity<>(address, OK);
    }

    @Override
    public ResponseEntity<Void> deleteAddress(@PathVariable final UUID id) {
        addressService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<Address> createAddress(@RequestBody final AddressRequest address) {
        var newAddress = addressService.create(address);
        return new ResponseEntity<>(newAddress, CREATED);
    }

    @Override
    public ResponseEntity<Address> updateAddress(@PathVariable final UUID id,
                                                 @RequestBody final AddressRequest address) {
        var updatedAddress = addressService.update(id, address);
        return new ResponseEntity<>(updatedAddress, OK);
    }

}

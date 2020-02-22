package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.model.requests.AddressRequest;
import com.iit.aiposizi.lab2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressesApi {

    private final AddressService addressService;

    @GetMapping
    public List<Address> getAddresses() {
        return addressService.getAll();
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable UUID id) {
        return addressService.getById(id);
    }

    @PostMapping
    public Address createAddress(@RequestBody AddressRequest request) {
        return addressService.create(request);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable UUID id, @RequestBody AddressRequest request) {
        return addressService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable UUID id) {
        addressService.delete(id);
    }

}

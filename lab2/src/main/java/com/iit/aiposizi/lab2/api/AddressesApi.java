package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.model.requests.AddressRequest;
import com.iit.aiposizi.lab2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressesApi {

    private final AddressService addressService;

    @GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String test() {
//        var addresses = addressService.getAll();
        return "redirect:test-template";
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        var addresses = addressService.getAll();
        return new ResponseEntity<>(addresses, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable UUID id) {
        var address = addressService.getById(id);
        return new ResponseEntity<>(address, OK);
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressRequest request) {
        var address = addressService.create(request);
        return new ResponseEntity<>(address, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable UUID id, @RequestBody AddressRequest request) {
        var updatedAddress = addressService.update(id, request);
        return new ResponseEntity<>(updatedAddress, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAddress(@PathVariable UUID id) {
        addressService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}

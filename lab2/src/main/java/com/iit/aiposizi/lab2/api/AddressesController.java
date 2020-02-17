package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/addresses")
public class AddressesController {

    private static final String REDIRECT_ALL_ADDRESSES_PATH = "redirect:/addresses/all";//TODO: refactor here

    private final AddressService addressService;

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("address", new Address());
        return "create-address";
    }

    @GetMapping("/all")
    public String getAllPage(Model model) {
        model.addAttribute("addresses", addressService.getAll());
        return "addresses";
    }

    @GetMapping("/{id}/update")//TODO: front logging
    public String updatePage(@PathVariable UUID id, Model model) {
        var address = addressService.getById(id);
        model.addAttribute("address", address);
        return "create-address";
    }

    @GetMapping("/{id}/delete")
    public String deleteRoute(@PathVariable UUID id) {
        addressService.delete(id);
        return REDIRECT_ALL_ADDRESSES_PATH;
    }

    @PostMapping("/create")//TODO: check if not number
    public String create(@RequestBody Address address) {
        addressService.create(address);
        return REDIRECT_ALL_ADDRESSES_PATH;
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable UUID id, @RequestBody Address address) {
        addressService.update(id, address);
        return REDIRECT_ALL_ADDRESSES_PATH;
    }

}

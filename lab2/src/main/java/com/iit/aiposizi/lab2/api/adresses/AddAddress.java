package com.iit.aiposizi.lab2.api.adresses;

import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.model.requests.AddressRequest;
import com.iit.aiposizi.lab2.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/addresses/add")
public class AddAddress {

    private final AddressService addressService;

    @GetMapping
    public String addPage(Model model){
        model.addAttribute("addressesList", addressService.getAll());
        model.addAttribute("address", new Address());
        return "addAddress";
    }

    @PostMapping
    public String save(AddressRequest request){
        addressService.create(request);
        log.info("add new address");
        return "redirect:/routes/all";//TODO: return created model, not list
    }

}


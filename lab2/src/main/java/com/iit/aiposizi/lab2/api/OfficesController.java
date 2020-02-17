package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Office;
import com.iit.aiposizi.lab2.service.AddressService;
import com.iit.aiposizi.lab2.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/offices")
public class OfficesController {

    private static final String REDIRECT_ALL_OFFICES_PATH = "redirect:/offices/all";//TODO: refactor here

    private final AddressService addressService;
    private final OfficeService officeService;

    @GetMapping("/{addressId}/create")
    public String createPage(@PathVariable UUID addressId, Model model) {
        var address = addressService.getById(addressId);
        var office = new Office();
        office.setAddress(address);
        model.addAttribute("office", office);
        return "create-office";
    }

    @GetMapping("/all")
    public String getAllPage(Model model) {
        model.addAttribute("offices", officeService.getAll());
        return "offices";
    }

    @GetMapping("/{id}/update")//TODO: front logging
    public String updatePage(@PathVariable UUID id, Model model) {
        var office = officeService.getById(id);
        model.addAttribute("office", office);
        return "create-office";
    }

    @GetMapping("/{id}/delete")
    public String deleteRoute(@PathVariable UUID id) {
        officeService.delete(id);
        return REDIRECT_ALL_OFFICES_PATH;
    }

    @PostMapping("/create")
    public String create(@RequestBody Office office) {
        officeService.create(office);
        return REDIRECT_ALL_OFFICES_PATH;
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable UUID id, @RequestBody Office office) {
        officeService.update(id, office);
        return REDIRECT_ALL_OFFICES_PATH;
    }

}

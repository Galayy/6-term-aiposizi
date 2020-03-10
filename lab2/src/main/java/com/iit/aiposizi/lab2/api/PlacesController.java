package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Place;
import com.iit.aiposizi.lab2.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlacesController {

    private static final String REDIRECT_HOME_PATH = "redirect:/";

    private final PlaceService placeService;

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable UUID id, Model model) {
        var place = placeService.getById(id);
        model.addAttribute("place", place);
        return "unplace-employee";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable UUID id) {
        placeService.delete(id);
        return REDIRECT_HOME_PATH;
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable UUID id, Place place) {
        placeService.update(id, place);
        return REDIRECT_HOME_PATH;
    }

}

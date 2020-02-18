package com.iit.aiposizi.lab2.api;

import com.iit.aiposizi.lab2.model.Office;
import com.iit.aiposizi.lab2.model.Room;
import com.iit.aiposizi.lab2.service.OfficeService;
import com.iit.aiposizi.lab2.service.RoomService;
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
@RequestMapping("/offices")
public class OfficesController {

    private static final String REDIRECT_ALL_OFFICES_PATH = "redirect:/offices/all";//TODO: refactor here

    private final OfficeService officeService;
    private final RoomService roomService;

    @GetMapping("/all")
    public String getAllPage(Model model) {
        model.addAttribute("offices", officeService.getAll());
        return "offices";
    }

    @GetMapping("/{id}/rooms")
    public String getRoomsByOfficeIdPage(@PathVariable UUID id, Model model) {
        model.addAttribute("rooms", roomService.getAllByOfficeId(id));
        return "rooms";
    }

    @GetMapping("/{id}/create-room")
    public String createRoomPage(@PathVariable UUID id, Model model) {
        var office = officeService.getById(id);
        var room = new Room();
        room.setOffice(office);
        model.addAttribute("room", room);
        return "create-room";
    }

    @GetMapping("/{id}/update")//TODO: front logging
    public String updatePage(@PathVariable UUID id, Model model) {
        var office = officeService.getById(id);
        model.addAttribute("office", office);
        return "create-office";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable UUID id) {
        officeService.delete(id);
        return REDIRECT_ALL_OFFICES_PATH;
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable UUID id, Office office) {
        officeService.update(id, office);
        return REDIRECT_ALL_OFFICES_PATH;
    }

    @PostMapping("/{id}/create-room")
    public String createRoom(@PathVariable("id") UUID officeId, Room room) {
        var office = officeService.getById(officeId);
        room.setOffice(office);
        roomService.create(room);
        return "redirect:/offices/{id}/rooms";
    }

}

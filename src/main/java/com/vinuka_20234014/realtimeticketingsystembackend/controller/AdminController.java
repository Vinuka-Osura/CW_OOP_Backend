package com.vinuka_20234014.realtimeticketingsystembackend.controller;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;
import com.vinuka_20234014.realtimeticketingsystembackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-vendor")
    public String addVendor(@RequestBody User user) {
        adminService.addVendor(user);
        return "Vendor added successfully.";
    }

    @DeleteMapping("/remove-vendor")
    public String removeVendor(@RequestParam int userId) {
        adminService.removeVendor(userId);
        return "Vendor removed successfully.";
    }

    @PostMapping("/create-event")
    public String createEvent(@RequestBody Event event) {
        adminService.addEvent(event);
        return "Event created successfully.";
    }

    @DeleteMapping("/delete-event")
    public String deleteEvent(@RequestParam int eventId) {
        adminService.deleteEvent(eventId);
        return "Event deleted successfully.";
    }
}
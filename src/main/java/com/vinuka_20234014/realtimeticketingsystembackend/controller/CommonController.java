package com.vinuka_20234014.realtimeticketingsystembackend.controller;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;
import com.vinuka_20234014.realtimeticketingsystembackend.service.CommonService;
import com.vinuka_20234014.realtimeticketingsystembackend.util.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService eventService;

    @Autowired
    private TicketPool ticketPool;

    @GetMapping("/get-events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/ticket-availability/{eventId}")
    public int getAvailableTickets(@PathVariable int eventId) {
        return ticketPool.getAvailableTickets(eventId);
    }

    @GetMapping("/vendors")
    public List<User> getAllVendors() {
        return eventService.getAllVendors();
    }

    @GetMapping("/ticket-availability")
    public Map<Integer, Integer> getAllAvailableTickets() {
        return ticketPool.getTicketPoolSnapshot();
    }
}

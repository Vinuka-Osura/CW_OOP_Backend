package com.vinuka_20234014.realtimeticketingsystembackend.service.impl;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.EventRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.UserRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addEvent(Event event) {
        if (event.getTicketCount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket count cannot be negative.");
        }
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(int eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with ID " + eventId + " not found.");
        }
        eventRepository.deleteById(eventId);
    }

    @Override
    public void addVendor(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vendor with username " + user.getUsername() + " already exists.");
        }
        user.setRole("vendor"); // Ensure the role is set to vendor
        userRepository.save(user);
    }

    @Override
    public void removeVendor(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor with ID " + userId + " not found.");
        }
        userRepository.deleteById(userId);
    }
}



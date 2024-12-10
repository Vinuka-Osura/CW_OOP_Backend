package com.vinuka_20234014.realtimeticketingsystembackend.service.impl;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.EventRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.UserRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<User> getAllVendors() {
        return userRepository.findAll()
                .stream()
                .filter(user -> "vendor".equalsIgnoreCase(user.getRole()))
                .collect(Collectors.toList());
    }
}

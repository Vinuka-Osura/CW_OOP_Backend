package com.vinuka_20234014.realtimeticketingsystembackend.service;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;

import java.util.List;

public interface AdminService {
    void addEvent(Event event);
    void deleteEvent(int eventId);
    void addVendor(User user); // New method
    void removeVendor(int userId); // New method
}

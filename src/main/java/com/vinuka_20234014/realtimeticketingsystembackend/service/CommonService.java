package com.vinuka_20234014.realtimeticketingsystembackend.service;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;

import java.util.List;

public interface CommonService {

    List<Event> getAllEvents();

    List<User> getAllVendors();

}

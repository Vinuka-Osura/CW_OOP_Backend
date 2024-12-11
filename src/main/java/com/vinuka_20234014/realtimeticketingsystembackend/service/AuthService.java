package com.vinuka_20234014.realtimeticketingsystembackend.service;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;

import java.util.Map;

public interface AuthService {

    void registerUser(User user);

    Map<String, String> loginUser(String username, String password);


}



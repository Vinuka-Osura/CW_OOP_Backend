package com.vinuka_20234014.realtimeticketingsystembackend.repository;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);  // Change return type to Optional
}

package com.vinuka_20234014.realtimeticketingsystembackend.repository;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.ActCustomers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActCustomerRepository extends JpaRepository<ActCustomers, Integer> {
    Optional<ActCustomers> findByCustomerId(int customerId);
}

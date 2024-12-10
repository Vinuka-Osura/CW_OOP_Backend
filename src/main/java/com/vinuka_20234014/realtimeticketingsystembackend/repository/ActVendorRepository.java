package com.vinuka_20234014.realtimeticketingsystembackend.repository;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.ActVendors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActVendorRepository extends JpaRepository<ActVendors, Integer> {
    Optional<ActVendors> findByVendorId(int vendorId);
}
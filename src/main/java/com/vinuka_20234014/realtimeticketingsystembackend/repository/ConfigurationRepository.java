package com.vinuka_20234014.realtimeticketingsystembackend.repository;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<SystemConfiguration, Integer> {
}

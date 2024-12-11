package com.vinuka_20234014.realtimeticketingsystembackend.service;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.SystemConfiguration;

public interface ConfigurationService {

    SystemConfiguration getCurrentConfiguration();

    SystemConfiguration saveConfiguration(SystemConfiguration configuration);

}

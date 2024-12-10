package com.vinuka_20234014.realtimeticketingsystembackend.service.impl;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.SystemConfiguration;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.ConfigurationRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public SystemConfiguration getCurrentConfiguration() {
        return configurationRepository.findAll().getFirst();
    }

    @Override
    @Transactional
    public SystemConfiguration saveConfiguration(SystemConfiguration configuration) {
        configurationRepository.deleteAll();
        return configurationRepository.save(configuration);
    }
}

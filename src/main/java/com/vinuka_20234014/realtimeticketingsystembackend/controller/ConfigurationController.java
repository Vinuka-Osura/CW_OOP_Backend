package com.vinuka_20234014.realtimeticketingsystembackend.controller;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.SystemConfiguration;
import com.vinuka_20234014.realtimeticketingsystembackend.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/config")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("/current")
    public SystemConfiguration getCurrentConfiguration() {
        return configurationService.getCurrentConfiguration();
    }

    @PostMapping("/save")
    public SystemConfiguration saveConfiguration(@RequestBody SystemConfiguration configuration) {
        return configurationService.saveConfiguration(configuration);
    }
}

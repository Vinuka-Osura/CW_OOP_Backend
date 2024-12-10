package com.vinuka_20234014.realtimeticketingsystembackend.controller;

import org.springframework.web.bind.annotation.*;
import com.vinuka_20234014.realtimeticketingsystembackend.service.impl.VendorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorServiceImpl vendorService;

    @PostMapping("/start")
    public String startVendor(@RequestParam int vendorId, @RequestParam int eventId, @RequestParam int releaseRate) {
        vendorService.startVendor(vendorId, eventId, releaseRate);
        return "Vendor " + vendorId + " started.";
    }

    @DeleteMapping("/stop/{vendorId}")
    public String stopVendor(@PathVariable int vendorId) {
        vendorService.stopVendorById(vendorId);
        return "Vendor " + vendorId + " stopped.";
    }

    @PostMapping("/stop-all")
    public String stopAllVendors() {
        vendorService.stopAllVendors();
        return "All vendor threads stopped.";
    }

    @GetMapping("/list")
    public List<Integer> listActiveVendors() {
        return vendorService.getActiveVendorIds();
    }
}


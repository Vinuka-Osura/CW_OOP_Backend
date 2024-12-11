package com.vinuka_20234014.realtimeticketingsystembackend.controller;

import com.vinuka_20234014.realtimeticketingsystembackend.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/start")
    public String startCustomer(@RequestParam int customerId, @RequestParam int eventId, @RequestParam int purchaseRate) {
        customerService.startCustomer(customerId, eventId, purchaseRate);
        return "Customer " + customerId + " started.";
    }

    @PostMapping("/stop/{customerId}")
    public String stopCustomer(@PathVariable int customerId) {
        customerService.stopCustomerById(customerId);
        return "Customer " + customerId + " stopped.";
    }

    @PostMapping("/stop-all")
    public String stopAllCustomers() {
        customerService.stopAllCustomers();
        return "All customer threads stopped.";
    }

    @GetMapping("/list")
    public List<Integer> listActiveCustomers() {
        return customerService.getActiveCustomerIds();
    }
}


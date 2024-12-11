package com.vinuka_20234014.realtimeticketingsystembackend.service.impl;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.ActVendors;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.SystemConfiguration;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.Transaction;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.ActVendorRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.ConfigurationRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.EventRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.TransactionRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.service.VendorService;
import com.vinuka_20234014.realtimeticketingsystembackend.util.TicketPool;
import com.vinuka_20234014.realtimeticketingsystembackend.util.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private ActVendorRepository vendorRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ConfigurationRepository systemConfigurationRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TicketPool ticketPool;

    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Thread> vendorThreads = new ArrayList<>();

    @Override
    public void startVendor(int vendorId, int eventId, int releaseRate) {

        eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event ID not found"));

        SystemConfiguration config = systemConfigurationRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "System configuration not set"));

        if (releaseRate > config.getTicketReleaseRate()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exceeds max ticket release rate");
        }

        Vendor vendor = new Vendor(vendorId, ticketPool, eventId, releaseRate);
        Thread thread = new Thread(() -> {
            while (vendor.isRunning()) {
                ticketPool.addTickets(eventId, releaseRate, vendorId);
                updateEventTicketCount(eventId, releaseRate);
                updateVendorTickets(vendorId, releaseRate);
                logTransaction(vendorId, eventId, "Vendor", releaseRate);

                try {
                    Thread.sleep(3000); // 3-second interval
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        ActVendors actVendor = vendorRepository.findByVendorId(vendorId)
                .orElse(new ActVendors(0, vendorId, eventId, releaseRate, 0, true));

        actVendor.setReleaseRate(releaseRate);
        actVendor.setEventId(eventId);
        actVendor.setActive(true);
        vendorRepository.save(actVendor);

        vendors.add(vendor);
        vendorThreads.add(thread);
        thread.start();
        System.out.println("Vendor " + vendorId + " started for Event ID: " + eventId);
    }

    @Override
    public List<Integer> getActiveVendorIds() {
        return vendorRepository.findAll().stream()
                .filter(ActVendors::isActive)
                .map(ActVendors::getVendorId)
                .toList();
    }

    @Override
    public void stopVendorById(int vendorId) {
        ActVendors actVendor = vendorRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor ID not found"));

        // Stop vendor thread
        vendors.stream()
                .filter(vendor -> vendor.getVendorId() == vendorId)
                .findFirst()
                .ifPresent(Vendor::stop);

        // Update ActVendor record
        actVendor.setActive(false);
        vendorRepository.save(actVendor);

        System.out.println("Vendor " + vendorId + " stopped.");
    }

    @Override
    public void stopAllVendors() {

        vendors.forEach(Vendor::stop);
        vendors.clear();
        vendorThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        vendorThreads.clear();

        // Update all ActVendor records
        vendorRepository.findAll().forEach(vendor -> {
            vendor.setActive(false);
            vendorRepository.save(vendor);
        });

        System.out.println("All vendor threads stopped.");
    }

    private void logTransaction(int userId, int eventId, String userType, int ticketCount) {
        Transaction transaction = new Transaction(0, eventId, userId, userType, ticketCount, LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    private void updateVendorTickets(int vendorId, int ticketCount) {
        ActVendors actVendor = vendorRepository.findByVendorId(vendorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor ID not found"));
        actVendor.setTicketCount(actVendor.getTicketCount() + ticketCount);
        vendorRepository.save(actVendor);
    }

    private void updateEventTicketCount(int eventId, int ticketCount) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event ID not found"));
        event.setTicketCount(event.getTicketCount() + ticketCount);
        eventRepository.save(event);
    }
}
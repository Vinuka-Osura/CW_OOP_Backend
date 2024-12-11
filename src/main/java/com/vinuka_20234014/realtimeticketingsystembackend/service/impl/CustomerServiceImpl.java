package com.vinuka_20234014.realtimeticketingsystembackend.service.impl;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.ActCustomers;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.Event;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.SystemConfiguration;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.Transaction;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.ActCustomerRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.ConfigurationRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.EventRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.TransactionRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.service.CustomerService;
import com.vinuka_20234014.realtimeticketingsystembackend.util.Customer;
import com.vinuka_20234014.realtimeticketingsystembackend.util.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private TicketPool ticketPool;
    @Autowired
    private ActCustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ConfigurationRepository systemConfigurationRepository;
    @Autowired
    private EventRepository eventRepository;

    private final List<Customer> customers = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();


    @Override
    public void startCustomer(int customerId, int eventId, int purchaseRate) {

        eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event ID not found"));

        SystemConfiguration config = systemConfigurationRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("SystemConfiguration not set in the database."));

        if (purchaseRate > config.getCustomerRetrievalRate()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exceeds max ticket retrieval rate");
        }

        Customer customer = new Customer(customerId, ticketPool, eventId, purchaseRate);
        Thread thread = new Thread(() -> {
            while (customer.isRunning()) {
                boolean success = ticketPool.removeTickets(eventId, purchaseRate, customerId);
                if (success) {
                    updateEventTicketCount(eventId, purchaseRate);
                    updateCustomerTickets(customerId, purchaseRate);
                    logTransaction(customerId, eventId, "Customer", purchaseRate);

                }
                try {
                    Thread.sleep(2000); // Simulate time interval between purchases
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        ActCustomers actCustomer = customerRepository.findByCustomerId(customerId)
                .orElse(new ActCustomers(0, customerId, eventId, purchaseRate, 0, true));
        actCustomer.setPurchaseRate(purchaseRate);
        actCustomer.setEventId(eventId);
        actCustomer.setActive(true);
        customerRepository.save(actCustomer);

        customers.add(customer);
        customerThreads.add(thread);
        thread.start();
        System.out.println("Customer " + customerId + " started for Event ID: " + eventId);
    }

    @Override
    public void stopCustomerById(int customerId) {
        // Find the customer in ActCustomers table
        ActCustomers actCustomer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer ID not found"));

        // Stop the customer thread
        customers.stream()
                .filter(customer -> customer.getCustomerId() == customerId)
                .findFirst()
                .ifPresent(Customer::stop);

        // Mark the customer as inactive in the database
        actCustomer.setActive(false);
        customerRepository.save(actCustomer);

        System.out.println("Customer " + customerId + " stopped.");
    }

    @Override
    public void stopAllCustomers() {
        customers.forEach(Customer::stop);
        customers.clear();

        customerThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        customerThreads.clear();

        customerRepository.findAll().forEach(customer -> {
            customer.setActive(false);
            customerRepository.save(customer);
        });

        System.out.println("All customer threads stopped.");
    }

    @Override
    public List<Integer> getActiveCustomerIds() {
        return customerRepository.findAll().stream()
                .filter(ActCustomers::isActive)
                .map(ActCustomers::getCustomerId)
                .toList();
    }

    private void logTransaction(int userId, int eventId, String userType, int ticketCount) {
        Transaction transaction = new Transaction(0, eventId, userId, userType, ticketCount, LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    private void updateCustomerTickets(int customerId, int ticketCount) {
        ActCustomers actCustomer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer ID not found"));
        actCustomer.setTicketCount(actCustomer.getTicketCount() + ticketCount);
        customerRepository.save(actCustomer);
    }

    private void updateEventTicketCount(int eventId, int ticketCount) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event ID not found"));
        event.setTicketCount(event.getTicketCount() - ticketCount);
        eventRepository.save(event);
    }
}

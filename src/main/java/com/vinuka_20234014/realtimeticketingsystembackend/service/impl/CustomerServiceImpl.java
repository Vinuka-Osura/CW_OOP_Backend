package com.vinuka_20234014.realtimeticketingsystembackend.service.impl;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Transaction;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.TransactionRepository;
import com.vinuka_20234014.realtimeticketingsystembackend.service.CustomerService;
import com.vinuka_20234014.realtimeticketingsystembackend.util.Customer;
import com.vinuka_20234014.realtimeticketingsystembackend.util.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final TicketPool ticketPool;
    private final List<Customer> customers;
    private final List<Thread> customerThreads;

    @Autowired
    public CustomerServiceImpl(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.customers = new ArrayList<>();
        this.customerThreads = new ArrayList<>();
    }

    @Autowired
    private TransactionRepository transactionRepository;

    // Start a new customer thread
    @Override
    public void startCustomer(int customerId, int eventId, int purchaseRate) {
        Customer customer = new Customer(customerId, ticketPool, eventId, purchaseRate);
        Thread thread = new Thread(() -> {
            while (customer.isRunning()) {
                if (ticketPool.removeTickets(eventId, purchaseRate)) {
                    transactionRepository.save(new Transaction(0, eventId, customerId, "Customer", purchaseRate, LocalDateTime.now()));
                }
            }
        });
        customerThreads.add(thread);
        thread.start();
        System.out.println("Customer " + customerId + " started for Event ID: " + eventId);
    }

    // Stop all customer threads
    @Override
    public void stopAllCustomers() {
        for (Customer customer : customers) {
            customer.stop(); // Gracefully stop the customer
        }
        customers.clear();
        for (Thread thread : customerThreads) {
            try {
                thread.join(); // Ensure all threads have stopped
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error while stopping customer threads.");
            }
        }
        customerThreads.clear();
        System.out.println("All customer threads have been stopped.");
    }

    // Stop a specific customer by ID
    @Override
    public void stopCustomerById(int customerId) {
        Customer customerToStop = null;
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                customerToStop = customer;
                break;
            }
        }
        if (customerToStop != null) {
            customerToStop.stop(); // Gracefully stop the customer
            customers.remove(customerToStop);
            System.out.println("Customer " + customerId + " has been stopped.");
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }

    // List active customer IDs
    @Override
    public List<Integer> getActiveCustomerIds() {
        List<Integer> activeCustomerIds = new ArrayList<>();
        for (Customer customer : customers) {
            activeCustomerIds.add(customer.getCustomerId());
        }
        return activeCustomerIds;
    }
}

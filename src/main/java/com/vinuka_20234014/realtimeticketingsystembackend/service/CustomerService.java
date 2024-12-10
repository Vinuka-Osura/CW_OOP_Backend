package com.vinuka_20234014.realtimeticketingsystembackend.service;

import java.util.List;

public interface CustomerService {

    /**
     * Starts a new customer thread for a specific event.
     * @param customerId - Unique ID of the customer.
     * @param eventId - Event ID for which the customer is purchasing tickets.
     * @param purchaseRate - Number of tickets the customer attempts to purchase per interval.
     */
    void startCustomer(int customerId, int eventId, int purchaseRate);

    /**
     * Stops all running customer threads gracefully.
     */
    void stopAllCustomers();
    void stopCustomerById(int customerId); // New method to stop a specific customer

    List<Integer> getActiveCustomerIds(); // New method to list active customer IDs
}

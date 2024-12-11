package com.vinuka_20234014.realtimeticketingsystembackend.service;

import java.util.List;

public interface CustomerService {

    void startCustomer(int customerId, int eventId, int purchaseRate);

    void stopAllCustomers();

    void stopCustomerById(int customerId);

    List<Integer> getActiveCustomerIds();

}

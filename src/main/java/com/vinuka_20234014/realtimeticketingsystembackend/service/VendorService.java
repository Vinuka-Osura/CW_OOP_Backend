package com.vinuka_20234014.realtimeticketingsystembackend.service;

import java.util.List;

public interface VendorService {

    /**
     * Starts a new vendor thread for a specific event.
     * @param vendorId - Unique ID of the vendor.
     * @param eventId - Event ID for which the vendor is adding tickets.
     * @param releaseRate - Number of tickets the vendor releases per interval.
     */
    void startVendor(int vendorId, int eventId, int releaseRate);

    /**
     * Stops all running vendor threads gracefully.
     */
    void stopAllVendors();
    void stopVendorById(int vendorId); // New method to stop a specific vendor

    List<Integer> getActiveVendorIds(); // New method to list active vendor IDs
}

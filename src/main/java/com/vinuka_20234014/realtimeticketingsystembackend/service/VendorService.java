package com.vinuka_20234014.realtimeticketingsystembackend.service;

import java.util.List;

public interface VendorService {

    void startVendor(int vendorId, int eventId, int releaseRate);

    void stopAllVendors();

    void stopVendorById(int vendorId);

    List<Integer> getActiveVendorIds();

}

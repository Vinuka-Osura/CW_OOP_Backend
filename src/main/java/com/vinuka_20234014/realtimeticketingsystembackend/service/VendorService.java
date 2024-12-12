package com.vinuka_20234014.realtimeticketingsystembackend.service;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.ActCustomers;
import com.vinuka_20234014.realtimeticketingsystembackend.entity.ActVendors;

import java.util.List;

public interface VendorService {

    void startVendor(int vendorId, int eventId, int releaseRate);

    void stopAllVendors();

    void stopVendorById(int vendorId);

    List<ActVendors> getActiveVendors();

}

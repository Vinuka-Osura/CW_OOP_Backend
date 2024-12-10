package com.vinuka_20234014.realtimeticketingsystembackend.util;

import lombok.Getter;
public class Vendor implements Runnable {

    // Getter for vendorId
    @Getter
    private final int vendorId;
    private final TicketPool ticketPool;
    private final int eventId;
    private final int releaseRate; // Tickets released per interval

    @Getter
    private volatile boolean running; // To stop the thread safely

    public Vendor(int vendorId, TicketPool ticketPool, int eventId, int releaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.eventId = eventId;
        this.releaseRate = releaseRate;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                ticketPool.addTickets(eventId, releaseRate);
                System.out.println("Vendor " + vendorId + " added " + releaseRate + " tickets for Event ID: " + eventId);
                Thread.sleep(2000); // Simulate time interval between releases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt status
                System.out.println("Vendor " + vendorId + " interrupted.");
            }
        }
        System.out.println("Vendor " + vendorId + " has stopped.");
    }

    public void stop() {
        this.running = false;
    }

}

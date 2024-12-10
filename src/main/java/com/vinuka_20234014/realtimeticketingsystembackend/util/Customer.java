package com.vinuka_20234014.realtimeticketingsystembackend.util;

import lombok.Getter;

public class Customer implements Runnable {

    @Getter
    private final int customerId;
    private final TicketPool ticketPool;
    private final int eventId;
    private final int purchaseRate; // Tickets purchased per interval

    @Getter
    private volatile boolean running; // To stop the thread safely

    public Customer(int customerId, TicketPool ticketPool, int eventId, int purchaseRate) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.eventId = eventId;
        this.purchaseRate = purchaseRate;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                boolean success = ticketPool.removeTickets(eventId, purchaseRate);
                if (success) {
                    System.out.println("Customer " + customerId + " purchased " + purchaseRate + " tickets for Event ID: " + eventId);
                } else {
                    System.out.println("Customer " + customerId + " failed to purchase tickets for Event ID: " + eventId);
                }
                Thread.sleep(2000); // Simulate time interval between purchases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt status
                System.out.println("Customer " + customerId + " interrupted.");
            }
        }
        System.out.println("Customer " + customerId + " has stopped.");
    }

    public void stop() {
        this.running = false;
    }

}

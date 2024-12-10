package com.vinuka_20234014.realtimeticketingsystembackend.util;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.SystemConfiguration;
import com.vinuka_20234014.realtimeticketingsystembackend.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class TicketPool {

    private final ConcurrentHashMap<Integer, Integer> ticketPool; // Event ID -> Ticket Count
    private final Lock lock = new ReentrantLock(); // Lock for synchronization
    private final Condition ticketsAvailable = lock.newCondition(); // Condition to wait for tickets
    private final ConfigurationRepository configurationRepository;

    public TicketPool(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
        this.ticketPool = new ConcurrentHashMap<>();
    }

    private int getMaxTicketCapacity() {
        SystemConfiguration config = configurationRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("SystemConfiguration is not set in the database."));
        return config.getMaxTicketCapacity();
    }

    public void addTickets(int eventId, int quantity) {
        lock.lock();
        try {
            int maxCapacity = getMaxTicketCapacity();
            while (getTotalTickets() + quantity > maxCapacity) {
                System.out.println("Ticket pool full. Vendor thread waiting...");
                ticketsAvailable.await(); // Wait until tickets are available
            }
            ticketPool.merge(eventId, quantity, Integer::sum);
            System.out.println("Added " + quantity + " tickets for Event ID: " + eventId);
            ticketsAvailable.signalAll(); // Notify waiting threads
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while adding tickets.");
        } finally {
            lock.unlock();
        }
    }

    public boolean removeTickets(int eventId, int quantity) {
        lock.lock();
        try {
            while (ticketPool.getOrDefault(eventId, 0) < quantity) {
                System.out.println("Not enough tickets available for Event ID: " + eventId + ". Waiting...");
                ticketsAvailable.await(); // Wait until tickets are available
            }
            ticketPool.put(eventId, ticketPool.get(eventId) - quantity);
            System.out.println("Removed " + quantity + " tickets for Event ID: " + eventId);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted while waiting for tickets.");
            return false;
        } finally {
            lock.unlock();
        }
    }

    public int getTotalTickets() {
        return ticketPool.values().stream().mapToInt(Integer::intValue).sum();
    }

    // Get the number of available tickets for an event
    public int getAvailableTickets(int eventId) {
        lock.lock();
        try {
            return ticketPool.getOrDefault(eventId, 0);
        } finally {
            lock.unlock();
        }
    }

    public Map<Integer, Integer> getTicketPoolSnapshot() {
        lock.lock();
        try {
            return new HashMap<>(ticketPool); // Return a copy of the ticket pool
        } finally {
            lock.unlock();
        }
    }
}

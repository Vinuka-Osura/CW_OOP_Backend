package com.vinuka_20234014.realtimeticketingsystembackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int totalTickets; // Total available tickets
    private int ticketReleaseRate; // Tickets released per vendor thread
    private int customerRetrievalRate; // Tickets purchased per customer thread
    private int maxTicketCapacity; // Maximum tickets in the system
}

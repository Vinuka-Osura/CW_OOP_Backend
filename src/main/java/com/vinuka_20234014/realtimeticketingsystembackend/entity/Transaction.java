package com.vinuka_20234014.realtimeticketingsystembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @Column(nullable = false)
    private int eventId;

    @Column(nullable = false)
    private int userId; // Vendor or Customer ID

    @Column(nullable = false)
    private String userType; // 'Vendor' or 'Customer'

    @Column(nullable = false)
    private int ticketCount;

    @Column(nullable = false)
    private LocalDateTime timestamp; // Transaction time

    //private String transactionType;
}
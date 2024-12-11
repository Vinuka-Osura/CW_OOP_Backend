package com.vinuka_20234014.realtimeticketingsystembackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ActCustomers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int customerId;

    private int eventId;

    private int purchaseRate;

    private int ticketCount;

    private boolean active;
}

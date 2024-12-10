package com.vinuka_20234014.realtimeticketingsystembackend.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ActVendors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int vendorId;

    private int eventId;

    private int releaseRate;

    private int ticketCount;

    private boolean active;


}

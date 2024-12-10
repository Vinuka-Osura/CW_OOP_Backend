package com.vinuka_20234014.realtimeticketingsystembackend.repository;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByEventEventIdAndIsAvailableTrue(int eventId);
}
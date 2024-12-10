package com.vinuka_20234014.realtimeticketingsystembackend.repository;

import com.vinuka_20234014.realtimeticketingsystembackend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}

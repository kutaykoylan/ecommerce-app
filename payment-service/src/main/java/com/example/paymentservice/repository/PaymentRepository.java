package com.example.paymentservice.repository;

import com.example.paymentservice.entity.Payment;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
}

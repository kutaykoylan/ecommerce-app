package com.example.paymentservice.repository;

import com.example.paymentservice.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

    Optional<Payment> findByIdAndVersion(Long id, Long version);

    Page<Payment> findAll(Pageable pageable);
}

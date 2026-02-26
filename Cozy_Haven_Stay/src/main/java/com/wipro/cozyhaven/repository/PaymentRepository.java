package com.wipro.cozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}

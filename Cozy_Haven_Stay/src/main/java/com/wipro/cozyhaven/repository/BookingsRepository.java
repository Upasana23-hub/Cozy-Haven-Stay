package com.wipro.cozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {

}

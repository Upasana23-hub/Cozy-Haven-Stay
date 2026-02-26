package com.wipro.cozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

}

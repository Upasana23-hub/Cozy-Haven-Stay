package com.wipro.cozyhaven.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Reviews")
@NoArgsConstructor 
@AllArgsConstructor 
@Getter
@Setter
@ToString
public class Review {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reviewId", nullable = false)
	private Long reviewId;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
	
	@Column(name = "rating", nullable = false)
	private Integer rating;
	
	@Column(name = "comment", nullable = false)
	private String comment;
	
	@Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
	
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
	
}

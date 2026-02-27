package com.wipro.cozyhaven.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDTO {
    private Long reviewId;
    private Long userId;
    private Long hotelId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
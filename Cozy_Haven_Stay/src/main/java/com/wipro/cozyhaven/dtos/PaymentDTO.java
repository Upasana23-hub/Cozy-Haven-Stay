package com.wipro.cozyhaven.dto;

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
public class PaymentDTO {
    private Long paymentId;
    private Long bookingId;
    private Long userId; 
    private Long hotelId;
    private Double amount;
    private String paymentMode;
    private String paymentStatus;
    private LocalDateTime paymentDate;
}
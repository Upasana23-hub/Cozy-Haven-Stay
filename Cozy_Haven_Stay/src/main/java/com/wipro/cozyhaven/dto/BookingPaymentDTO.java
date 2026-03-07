package com.wipro.cozyhaven.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingPaymentDTO {

    private Long userId;
    private Long hotelId;
    private Long roomId;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private int adults;
    private int children;
    private int noOfRooms;

    private Double totalAmount;

    private String paymentMode;
}
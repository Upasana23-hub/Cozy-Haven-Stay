package com.wipro.cozyhaven.services;

import java.util.List;

import com.wipro.cozyhaven.dtos.PaymentDTO;

public interface IPaymentService {
    PaymentDTO addPayment(PaymentDTO paymentDTO);
    PaymentDTO getPaymentById(Long paymentId);
    List<PaymentDTO> getAllPayments();
    PaymentDTO getPaymentByBookingId(Long bookingId);
    List<PaymentDTO> getPaymentsByUserId(Long userId);
    List<PaymentDTO> getPaymentsByOwnerId(Long ownerId);
    PaymentDTO updatePaymentStatus(Long paymentId, String status);
}
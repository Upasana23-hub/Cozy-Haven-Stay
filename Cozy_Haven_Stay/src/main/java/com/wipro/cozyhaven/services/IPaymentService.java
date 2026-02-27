package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO addPayment(PaymentDTO paymentDTO);
    PaymentDTO getPaymentById(Long paymentId);
    List<PaymentDTO> getAllPayments();
    PaymentDTO getPaymentByBookingId(Long bookingId);
    List<PaymentDTO> getPaymentsByUserId(Long userId);
    List<PaymentDTO> getPaymentsByOwnerId(Long ownerId);
    PaymentDTO updatePaymentStatus(Long paymentId, String status);
}
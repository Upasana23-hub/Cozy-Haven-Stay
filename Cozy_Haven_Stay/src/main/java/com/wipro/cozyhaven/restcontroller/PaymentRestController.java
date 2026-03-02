package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.PaymentDTO;
import com.wipro.cozyhaven.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PaymentRestController {
	@Autowired
    private PaymentService paymentService;
	
	@PostMapping("/bookings/payments/{bookingId}")
    public ResponseEntity<PaymentDTO> addPayment(@PathVariable Long bookingId, @Valid @RequestBody PaymentDTO paymentDTO) {
        paymentDTO.setBookingId(bookingId);
        PaymentDTO createdPayment = paymentService.addPayment(paymentDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }
	
	@GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
        PaymentDTO payment = paymentService.getPaymentById(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
	
	@GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
	
	@GetMapping("/bookings/getpayment/{bookingId}")
    public ResponseEntity<PaymentDTO> getPaymentByBooking(@PathVariable Long bookingId) {
        PaymentDTO payment = paymentService.getPaymentByBookingId(bookingId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
	
	@GetMapping("/users/payments/{userId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByUser(@PathVariable Long userId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByUserId(userId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
	
	@GetMapping("/owners/payments/{ownerId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByOwner(@PathVariable Long ownerId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByOwnerId(ownerId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
	
	@PatchMapping("/payments/status/{paymentId}")
    public ResponseEntity<PaymentDTO> updatePaymentStatus(@PathVariable Long paymentId,@RequestParam String status) {       
        PaymentDTO updatedPayment = paymentService.updatePaymentStatus(paymentId, status);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }
}

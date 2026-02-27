package com.wipro.cozyhaven.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dtos.PaymentDTO;
import com.wipro.cozyhaven.entities.Bookings;
import com.wipro.cozyhaven.entities.Payment;
import com.wipro.cozyhaven.exception.ResourceNotFoundException;
import com.wipro.cozyhaven.repositories.BookingsRepository;
import com.wipro.cozyhaven.repositories.HotelRepository;
import com.wipro.cozyhaven.repositories.PaymentRepository;

@Service
public class PaymentServiceImpl implements IPaymentService {

	
	@Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingsRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public PaymentDTO addPayment(PaymentDTO paymentDTO) {
        Bookings booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMode(paymentDTO.getPaymentMode());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setPaymentDate(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(saved.getPaymentId());
        dto.setBookingId(saved.getBooking().getBookingId());
        dto.setUserId(saved.getBooking().getUser().getUserId());
        dto.setHotelId(saved.getBooking().getHotel().getHotelId());
        dto.setAmount(saved.getAmount());
        dto.setPaymentMode(saved.getPaymentMode());
        dto.setPaymentStatus(saved.getPaymentStatus());
        dto.setPaymentDate(saved.getPaymentDate());
        return dto;
    }
    
    

    @Override
    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBooking().getBookingId());
        dto.setUserId(payment.getBooking().getUser().getUserId());
        dto.setHotelId(payment.getBooking().getHotel().getHotelId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMode(payment.getPaymentMode());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }
    

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDTO> dtos = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDTO dto = new PaymentDTO();
            dto.setPaymentId(payment.getPaymentId());
            dto.setBookingId(payment.getBooking().getBookingId());
            dto.setUserId(payment.getBooking().getUser().getUserId());
            dto.setHotelId(payment.getBooking().getHotel().getHotelId());
            dto.setAmount(payment.getAmount());
            dto.setPaymentMode(payment.getPaymentMode());
            dto.setPaymentStatus(payment.getPaymentStatus());
            dto.setPaymentDate(payment.getPaymentDate());
            dtos.add(dto);
        }
        return dtos;
    }
    

    @Override
    public PaymentDTO getPaymentByBookingId(Long bookingId) {
        Payment payment = paymentRepository.findByBookingBookingId(bookingId);
        if (payment == null) {
            throw new ResourceNotFoundException("Payment not found for booking id " + bookingId);
        }

        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBooking().getBookingId());
        dto.setUserId(payment.getBooking().getUser().getUserId());
        dto.setHotelId(payment.getBooking().getHotel().getHotelId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMode(payment.getPaymentMode());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }
    

    @Override
    public List<PaymentDTO> getPaymentsByUserId(Long userId) {
        List<Payment> payments = paymentRepository.findByBookingUserUserId(userId);
        List<PaymentDTO> dtos = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDTO dto = new PaymentDTO();
            dto.setPaymentId(payment.getPaymentId());
            dto.setBookingId(payment.getBooking().getBookingId());
            dto.setUserId(payment.getBooking().getUser().getUserId());
            dto.setHotelId(payment.getBooking().getHotel().getHotelId());
            dto.setAmount(payment.getAmount());
            dto.setPaymentMode(payment.getPaymentMode());
            dto.setPaymentStatus(payment.getPaymentStatus());
            dto.setPaymentDate(payment.getPaymentDate());
            dtos.add(dto);
        }
        return dtos;
    }


    @Override
    public List<PaymentDTO> getPaymentsByOwnerId(Long ownerId) {
        List<Payment> allPayments = paymentRepository.findAll();
        List<PaymentDTO> dtos = new ArrayList<>();
        for (Payment payment : allPayments) {
            if (payment.getBooking().getHotel().getOwner().getOwnerId().equals(ownerId)) {
                PaymentDTO dto = new PaymentDTO();
                dto.setPaymentId(payment.getPaymentId());
                dto.setBookingId(payment.getBooking().getBookingId());
                dto.setUserId(payment.getBooking().getUser().getUserId());
                dto.setHotelId(payment.getBooking().getHotel().getHotelId());
                dto.setAmount(payment.getAmount());
                dto.setPaymentMode(payment.getPaymentMode());
                dto.setPaymentStatus(payment.getPaymentStatus());
                dto.setPaymentDate(payment.getPaymentDate());
                dtos.add(dto);
            }
        }

        if (dtos.isEmpty()) {
            throw new ResourceNotFoundException("No payments found for owner id " + ownerId);
        }

        return dtos;
    }

    
    @Override
    public PaymentDTO updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        payment.setPaymentStatus(status);
        Payment updated = paymentRepository.save(payment);

        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(updated.getPaymentId());
        dto.setBookingId(updated.getBooking().getBookingId());
        dto.setUserId(updated.getBooking().getUser().getUserId());
        dto.setHotelId(updated.getBooking().getHotel().getHotelId());
        dto.setAmount(updated.getAmount());
        dto.setPaymentMode(updated.getPaymentMode());
        dto.setPaymentStatus(updated.getPaymentStatus());
        dto.setPaymentDate(updated.getPaymentDate());

        return dto;
    }
    
	

	

	
	
}

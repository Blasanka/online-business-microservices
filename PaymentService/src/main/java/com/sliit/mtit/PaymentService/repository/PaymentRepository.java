package com.sliit.mtit.PaymentService.repository;

import com.sliit.mtit.PaymentService.dto.PaymentResponse;
import com.sliit.mtit.PaymentService.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM payment WHERE user_id = :userId AND order_id = :orderId", nativeQuery = true)
    public List<Payment> findPaymentHistory(Long userId, Long orderId);

    @Query(value = "SELECT * FROM payment WHERE payment_reference = :paymentReference", nativeQuery = true)
    public List<Payment> findPaymentHistoryWithRef(String paymentReference);
}

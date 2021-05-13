package com.sliit.mtit.PaymentService.repository;

import com.sliit.mtit.PaymentService.dto.PaymentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentResponse, Long> {

    @Query(value = "SELECT * FROM abc_payments WHERE userId = :userId AND " +
            "orderId = :orderId AND paymentReference = :paymentReference", nativeQuery = true)
    public List<PaymentResponse> fetchPaymentHistory(Long userId, Long orderId, String paymentReference);
}

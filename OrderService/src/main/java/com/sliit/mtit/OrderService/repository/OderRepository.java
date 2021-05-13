package com.sliit.mtit.OrderService.repository;

import com.sliit.mtit.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM abc_orders WHERE id= :id AND user_id= :userId", nativeQuery = true)
    Optional<Order> findByUserId(Long id, Long userId);
}

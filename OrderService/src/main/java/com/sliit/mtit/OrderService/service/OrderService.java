package com.sliit.mtit.OrderService.service;

import com.sliit.mtit.OrderService.dto.GeneralResponse;
import com.sliit.mtit.OrderService.dto.OrderRequest;
import com.sliit.mtit.OrderService.dto.OrderResponse;
import com.sliit.mtit.OrderService.entity.Order;
import com.sliit.mtit.OrderService.repository.OderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OderRepository oderRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = oderRepository.save(new Order(orderRequest.getUserId(),
                orderRequest.getProductId(), "Pending"));
        return new OrderResponse(order.getId(), order.getDate(), order.getStatus());
    }

    public boolean cancelOrder(Long orderId) {
        try {
            oderRepository.deleteById(orderId);
            return true;
        } catch (Exception e) {
            Logger.getLogger("info").log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    public List<OrderResponse> fetchAllOrders() {
        List<Order> orders = oderRepository.findAll();
        return orders.stream()
                .filter(elt -> elt != null)
                .map(elt -> new OrderResponse(elt.getId(), elt.getDate(),
                        elt.getStatus(), elt.getUserId(), elt.getProductId()))
                .collect(Collectors.toList());
    }

    public OrderResponse fetchOrder(Long id, Long userId) {
        Optional<Order> order = oderRepository.findByUserId(id, userId);
        return new OrderResponse(order.get().getId(),
                order.get().getDate(),
                order.get().getStatus(),
                order.get().getUserId(),
                order.get().getProductId());
    }

    public GeneralResponse<OrderResponse> searchProductFromOrders(Long id) {
        Optional<Order> order = oderRepository.findById(id);

        if (order.isEmpty())
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "No orders found!",
                null
            );

        return new GeneralResponse<>(
            HttpStatus.OK.value(),
            null,
            new OrderResponse(order.get().getId(),
                    order.get().getDate(),
                    order.get().getStatus(),
                    order.get().getUserId(),
                    order.get().getProductId())
        );
    }
}

package com.sliit.mtit.OrderService.controller;

import com.sliit.mtit.OrderService.dto.*;
import com.sliit.mtit.OrderService.entity.Order;
import com.sliit.mtit.OrderService.service.OrderService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(consumes = "application/json", produces = "application/json", headers = "accessToken")
    public ResponseEntity<GeneralResponse<OrderResponse>> createOrder(
            @RequestBody OrderRequest orderRequest, @RequestHeader String accessToken) {

        GeneralResponse<OrderResponse> orderGeneralRes = orderService.createOrder(orderRequest, accessToken);
        return new ResponseEntity<>(orderGeneralRes, HttpStatus.resolve(orderGeneralRes.getStatus()));
    }

    @GetMapping(produces = "application/json", headers = {"accessToken"})
    public ResponseEntity<GeneralResponse<List<OrderResponse>>> getOrders(@RequestHeader String accessToken) {
        GeneralResponse<List<OrderResponse>> response = orderService.fetchAllOrders(accessToken);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(produces = "application/json", path = "/{id}", headers = {"userId", "accessToken"})
    public ResponseEntity<GeneralResponse<OrderResponse>> getOrder(@PathVariable Long id,
          @RequestHeader Long userId, @RequestHeader String accessToken) {

        GeneralResponse<OrderResponse> orderResponse = orderService.fetchOrder(id, userId, accessToken);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json", path = "/{orderId}")
    public ResponseEntity<GeneralResponse> cancelOrder(@PathVariable Long orderId, @RequestHeader String accessToken) {
        GeneralResponse response = orderService.cancelOrder(orderId, accessToken);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(produces = "application/json", params = "productId", headers = "accessToken")
    public ResponseEntity<GeneralResponse<OrderResponse>> searchProductFromOrders(
            @RequestParam String productName, @RequestHeader String accessToken) {

        GeneralResponse response = orderService.searchProductFromOrders(productName, accessToken);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}

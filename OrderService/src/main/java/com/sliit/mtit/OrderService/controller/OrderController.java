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

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {

        if (validateCreateOrderRequest(orderRequest))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        GeneralResponse<?> generalResponse = restTemplate.exchange(
                "http://localhost:8080/api/v1/users/"+orderRequest.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GeneralResponse<UserResponse>>() {}).getBody();
        UserResponse userResponse = (UserResponse) generalResponse.getBody();

        OrderResponse orderRes = orderService.createOrder(orderRequest);
        orderRes.setUserId(userResponse.getId());
        orderRes.setUsername(userResponse.getUsername());

        return new ResponseEntity<>(orderRes, HttpStatus.OK);
    }

    private boolean validateCreateOrderRequest(OrderRequest orderRequest) {
        return orderRequest.getUserId() == null || orderRequest.getProductId() == null;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderResponse>> getOrders() {
        List<OrderResponse> response = orderService.fetchAllOrders();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", path = "/{id}", headers = "userId")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id, @RequestHeader Long userId) {

        if (id == null || userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        OrderResponse orderResponse = orderService.fetchOrder(id, userId);

        if (orderResponse == null)
            return new ResponseEntity<>(null, HttpStatus.OK);

        GeneralResponse<UserResponse> generalResponse = restTemplate.exchange(
                "http://localhost:8080/api/v1/users/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GeneralResponse<UserResponse>>() {}).getBody();

        if (generalResponse.getBody() != null) {
            orderResponse.setUserId(generalResponse.getBody().getId());
            orderResponse.setUsername(generalResponse.getBody().getUsername());
        }
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json", path = "/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        boolean response = orderService.cancelOrder(orderId);
        if (response)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(produces = "application/json", params = "productId")
    public ResponseEntity<GeneralResponse<Order>> searchProductFromOrders(@RequestParam String productName) {
        GeneralResponse<ProductResponse> generalResponse = restTemplate.getForObject(
                "http://localhost:8082/api/v1/products?productName="+ productName,
                GeneralResponse.class);
        GeneralResponse response = orderService.searchProductFromOrders(
                generalResponse.getBody().getId());
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}

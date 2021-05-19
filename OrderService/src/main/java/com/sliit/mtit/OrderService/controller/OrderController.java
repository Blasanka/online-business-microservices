package com.sliit.mtit.OrderService.controller;

import com.sliit.mtit.OrderService.dto.*;
import com.sliit.mtit.OrderService.entity.Order;
import com.sliit.mtit.OrderService.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
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
    @ApiOperation(
            value = "Place new orders for logged in customer",
            notes = "By providing access token, can place new orders",
            consumes = "application/json",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "POST",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<OrderResponse>> createOrder(
            @RequestBody OrderRequest orderRequest, @RequestHeader String accessToken) {

        GeneralResponse<OrderResponse> orderGeneralRes = orderService.createOrder(orderRequest, accessToken);
        return new ResponseEntity<>(orderGeneralRes, HttpStatus.resolve(orderGeneralRes.getStatus()));
    }

    @GetMapping(produces = "application/json", headers = {"accessToken"})
    @ApiOperation(
            value = "Get all order details of particular user",
            notes = "Protected request that provides all order details of a single user",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "GET",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<List<OrderResponse>>> getOrders(@RequestHeader String accessToken) {
        GeneralResponse<List<OrderResponse>> response = orderService.fetchAllOrders(accessToken);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(produces = "application/json", path = "/{id}", headers = {"userId", "accessToken"})
    @ApiOperation(
            value = "Get single order detail of a single user",
            notes = "Protected request that provide order id as path variable to get specific order details",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "GET",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<OrderResponse>> getOrder(
          @ApiParam(name = "id", required = true) @PathVariable Long id,
          @RequestHeader Long userId, @RequestHeader String accessToken) {

        GeneralResponse<OrderResponse> orderResponse = orderService.fetchOrder(id, userId, accessToken);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json", path = "/{orderId}")
    @ApiOperation(
            value = "Cancel placed orders for logged in customer",
            notes = "By providing access token, and path variable order id can cancel placed order",
            consumes = "application/json",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "DELETE",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse> cancelOrder(@ApiParam(name = "orderId", required = true)
           @PathVariable Long orderId, @RequestHeader String accessToken) {
        GeneralResponse response = orderService.cancelOrder(orderId, accessToken);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(produces = "application/json", params = "productId", headers = "accessToken")
    @ApiOperation(
            value = "Search products within a order",
            notes = "Protected request that provide details of order's products by product name receives from Product microservice",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "GET",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<OrderResponse>> searchProductFromOrders(
            @ApiParam(name = "productName", required = true)
            @RequestParam String productName, @RequestHeader String accessToken) {

        GeneralResponse response = orderService.searchProductFromOrders(productName, accessToken);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}

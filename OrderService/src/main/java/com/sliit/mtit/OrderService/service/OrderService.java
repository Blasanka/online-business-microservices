package com.sliit.mtit.OrderService.service;

import com.sliit.mtit.OrderService.dto.*;
import com.sliit.mtit.OrderService.entity.Order;
import com.sliit.mtit.OrderService.repository.OderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OderRepository oderRepository;

    @Autowired
    RestTemplate restTemplate;

    private boolean validateCreateOrderRequest(OrderRequest orderRequest) {
        return orderRequest.getUserId() == null || orderRequest.getProductId() == null;
    }

    public GeneralResponse<OrderResponse> createOrder(OrderRequest orderRequest, String accessToken) {

        if (validateCreateOrderRequest(orderRequest))
            return getBadRequestGeneralResponse("User id and product id not provided");

        if (!checkAuthorization(accessToken))
            return getUnAuthorizedResponse("Unauthorized! cannot place order");

        GeneralResponse<?> generalResponse = restTemplate.exchange(
                "http://localhost:8080/api/v1/users/"+orderRequest.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GeneralResponse<UserResponse>>() {}).getBody();

        if (generalResponse == null || generalResponse.getBody() == null)
            return getBadRequestGeneralResponse("User does not exists!");

        UserResponse userResponse = (UserResponse) generalResponse.getBody();

        try {
            Order order = oderRepository.save(new Order(orderRequest.getUserId(),
                    orderRequest.getProductId(), "Pending"));

            if (order != null) {
                OrderResponse orderRes = new OrderResponse(order.getId(), order.getDate(), order.getStatus());
                orderRes.setUserId(userResponse.getId());
                orderRes.setUsername(userResponse.getUsername());

                return new GeneralResponse(
                        HttpStatus.OK.value(),
                        "Order placed successful, proceed with payment",
                        orderRes
                );
            } else {
                return getServerErrorGeneralResponse();
            }
        } catch (Exception e) {
            return getServerErrorGeneralResponse();
        }
    }

    public GeneralResponse cancelOrder(Long orderId, String accessToken) {
        if (!checkAuthorization(accessToken))
            return getUnAuthorizedResponse("Unauthorized! cannot proceed");

        try {
            oderRepository.deleteById(orderId);
            return new GeneralResponse(
                HttpStatus.NO_CONTENT.value(),
                "Successfully cancel order",
                null
            );
        } catch (Exception e) {
            Logger.getLogger("info").log(Level.SEVERE, e.getMessage());
            return new GeneralResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Failed to cancel order!",
                null
            );
        }
    }

    public GeneralResponse<List<OrderResponse>> fetchAllOrders(String accessToken) {
        if (!checkAuthorization(accessToken))
            return getUnAuthorizedResponse("Unauthorized! cannot proceed");

        List<Order> orders = oderRepository.findAll();

        if (orders == null)
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "No orders found!",
                Arrays.asList()
            );

        List<OrderResponse> responses = orders.stream()
                .filter(elt -> elt != null)
                .map(elt -> new OrderResponse(elt.getId(), elt.getDate(),
                        elt.getStatus(), elt.getUserId(), elt.getProductId()))
                .collect(Collectors.toList());

        return new GeneralResponse<>(
            HttpStatus.OK.value(),
            null,
            responses
        );
    }

    public GeneralResponse<OrderResponse> fetchOrder(Long id, Long userId, String accessToken) {
        if (id == null || userId == null)
            return getBadRequestGeneralResponse("Order id and user id cannot be empty");

        if (!checkAuthorization(accessToken))
            return getUnAuthorizedResponse("Unauthorized! cannot proceed");

        Optional<Order> order = oderRepository.findByUserId(id, userId);
        if (order == null)
            return new GeneralResponse<>(
                    HttpStatus.OK.value(),
                    "No order found!",
                    null
            );

        GeneralResponse<UserResponse> generalResponse = restTemplate.exchange(
                "http://localhost:8080/api/v1/users/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GeneralResponse<UserResponse>>() {}).getBody();

        OrderResponse orderResponse = new OrderResponse(order.get().getId(),
                order.get().getDate(),
                order.get().getStatus(),
                order.get().getUserId(),
                order.get().getProductId());

        if (generalResponse.getBody() != null) {
            orderResponse.setUserId(generalResponse.getBody().getId());
            orderResponse.setUsername(generalResponse.getBody().getUsername());
        }

        return new GeneralResponse<>(
            HttpStatus.OK.value(),
            null,
            orderResponse
        );
    }

    public GeneralResponse<OrderResponse> searchProductFromOrders(String productName, String accessToken) {
        if (!checkAuthorization(accessToken))
            return getUnAuthorizedResponse("Unauthorized! cannot proceed");

        // This is not implemented by the other member when this is implementing
        GeneralResponse<ProductResponse> generalResponse = restTemplate.getForObject(
                "http://localhost:8087/api/v1/products?productName="+ productName,
                GeneralResponse.class);

        Optional<Order> order = oderRepository.findById(generalResponse.getBody().getId());

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

    public boolean checkAuthorization(String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("accessToken", accessToken);

        var response = restTemplate.exchange(
                "http://localhost:8083/api/v1/auth",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Object.class
        );
        return response.getStatusCode() == HttpStatus.OK;
    }

    private GeneralResponse getUnAuthorizedResponse(String msg) {
        return new GeneralResponse(
                HttpStatus.UNAUTHORIZED.value(),
                msg,
                null
        );
    }

    private GeneralResponse getServerErrorGeneralResponse() {
        return new GeneralResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Failed to process order!",
                null
        );
    }

    private GeneralResponse<OrderResponse> getBadRequestGeneralResponse(String msg) {
        return new GeneralResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                msg,
                null
        );
    }

}

package com.sliit.mtit.PaymentService.controller;

import com.sliit.mtit.PaymentService.dto.CheckPaymentRequest;
import com.sliit.mtit.PaymentService.dto.GeneralResponse;
import com.sliit.mtit.PaymentService.dto.MakePaymentRequest;
import com.sliit.mtit.PaymentService.dto.PaymentResponse;
import com.sliit.mtit.PaymentService.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping(produces = "application/json", consumes = "application/json", headers = "accessToken")
    @ApiOperation(
            value = "Request to complete order by providing payment details",
            notes = "Protected request. complete order by third party payment integration",
            consumes = "application/json",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "POST",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<PaymentResponse>> makePayment(
            @RequestBody MakePaymentRequest paymentRequest, @RequestHeader String accessToken) {

        GeneralResponse<PaymentResponse> generalResponse = paymentService.makePayment(
                paymentRequest, accessToken);

        return new ResponseEntity<>(generalResponse, HttpStatus.resolve(generalResponse.getStatus()));
    }

    @GetMapping(produces = "application/json", path = "/{userId}", headers = "accessToken")
    @ApiOperation(
            value = "Check payment history by payment id",
            notes = "Protected request. Adding payment id as path variable can retrieve particular payment details",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "GET",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<List<PaymentResponse>>> checkPaymentHistoryByUserId(
        @ApiParam(name = "userId", required = true) @PathVariable Long userId, @RequestHeader String accessToken) {
        GeneralResponse<List<PaymentResponse>> generalResponse = paymentService.checkPaymentHistoryByUserId(
                userId, accessToken);

        return new ResponseEntity<>(generalResponse, HttpStatus.resolve(generalResponse.getStatus()));
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path = "/history", headers = "accessToken")
    @ApiOperation(
            value = "Check payment history of particular customer",
            notes = "Protected request. By providing order id and user id as request body, can let customer check their history",
            consumes = "application/json",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "POST",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<PaymentResponse>> checkPaymentHistory(
            @RequestBody CheckPaymentRequest checkPaymentRequest, @RequestHeader String accessToken) {


        GeneralResponse<PaymentResponse> generalResponse = paymentService.checkPaymentHistory(
                checkPaymentRequest, accessToken);

        return new ResponseEntity<>(generalResponse, HttpStatus.resolve(generalResponse.getStatus()));
    }

}

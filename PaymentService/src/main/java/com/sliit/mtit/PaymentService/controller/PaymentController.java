package com.sliit.mtit.PaymentService.controller;

import com.sliit.mtit.PaymentService.dto.CheckPaymentRequest;
import com.sliit.mtit.PaymentService.dto.GeneralResponse;
import com.sliit.mtit.PaymentService.dto.MakePaymentRequest;
import com.sliit.mtit.PaymentService.dto.PaymentResponse;
import com.sliit.mtit.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping(produces = "application/json", consumes = "application/json", headers = "accessToken")
    public ResponseEntity<GeneralResponse<PaymentResponse>> makePayment(
            @RequestBody MakePaymentRequest paymentRequest, @RequestHeader String accessToken) {

        GeneralResponse<PaymentResponse> generalResponse = paymentService.makePayment(
                paymentRequest, accessToken);

        return new ResponseEntity<>(generalResponse, HttpStatus.resolve(generalResponse.getStatus()));
    }

    @PostMapping(produces = "application/json", consumes = "application/json", headers = "accessToken")
    public ResponseEntity<GeneralResponse<List<PaymentResponse>>> checkPaymentHistory(
            @RequestBody CheckPaymentRequest checkPaymentRequest, @RequestHeader String accessToken) {

        GeneralResponse<List<PaymentResponse>> generalResponse = paymentService.checkPaymentHistory(
                checkPaymentRequest, accessToken);

        return new ResponseEntity<>(generalResponse, HttpStatus.resolve(generalResponse.getStatus()));
    }

}

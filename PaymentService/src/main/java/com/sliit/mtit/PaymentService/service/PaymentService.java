package com.sliit.mtit.PaymentService.service;

import com.sliit.mtit.PaymentService.dto.CheckPaymentRequest;
import com.sliit.mtit.PaymentService.dto.GeneralResponse;
import com.sliit.mtit.PaymentService.dto.MakePaymentRequest;
import com.sliit.mtit.PaymentService.dto.PaymentResponse;
import com.sliit.mtit.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    // Here goes third part payment integration like Stripe or Paypal or whatever
    public GeneralResponse<PaymentResponse> makePayment(
            MakePaymentRequest paymentRequest, String accessToken) {

        if (validateAccessToken(accessToken)) {
            var generalResponse = new GeneralResponse<PaymentResponse>(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized! Failed to proceed with payment.",
                    new PaymentResponse()
            );

        }

        return new GeneralResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized! Failed to proceed with payment.",
                null
        );
    }

    private boolean validateAccessToken(String accessToken) {
        // Dummy validation to prove that validation is here,
        // jsonwebtoken can be use to jwt management
        return (accessToken.length() > 12 && accessToken.split(".").length == 3);
    }

    public GeneralResponse<List<PaymentResponse>> checkPaymentHistory(
            CheckPaymentRequest checkPaymentRequest, String accessToken) {

        if (validateAccessToken(accessToken) && validateCheckPaymentRequest(checkPaymentRequest)) {
            try {
                List<PaymentResponse> payments = paymentRepository.fetchPaymentHistory(
                        checkPaymentRequest.getUserId(),
                        checkPaymentRequest.getOrderId(),
                        checkPaymentRequest.getPaymentReference()
                );
                GeneralResponse<List<PaymentResponse>> generalResponse = new GeneralResponse<>(
                        HttpStatus.OK.value(),
                        null,
                        payments
                );
                return generalResponse;
            } catch (Exception e) {
                return new GeneralResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Processing failed, Cannot fetch payment history!",
                        null
                );
            }
        }
        return new GeneralResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Unauthorized, failed to process request",
            null
        );
    }

    private boolean validateCheckPaymentRequest(CheckPaymentRequest checkPaymentRequest) {
        return checkPaymentRequest.getPaymentReference() != null && checkPaymentRequest.getPaymentReference() != ""
                && checkPaymentRequest.getOrderId() != null && checkPaymentRequest.getOrderId() != 0
                && checkPaymentRequest.getUserId() != null && checkPaymentRequest.getUserId() != 0;
    }
}

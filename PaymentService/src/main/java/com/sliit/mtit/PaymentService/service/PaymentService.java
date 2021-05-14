package com.sliit.mtit.PaymentService.service;

import com.sliit.mtit.PaymentService.dto.CheckPaymentRequest;
import com.sliit.mtit.PaymentService.dto.GeneralResponse;
import com.sliit.mtit.PaymentService.dto.MakePaymentRequest;
import com.sliit.mtit.PaymentService.dto.PaymentResponse;
import com.sliit.mtit.PaymentService.entity.Payment;
import com.sliit.mtit.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    // Here goes third part payment integration like Stripe or Paypal or whatever
    public GeneralResponse<PaymentResponse> makePayment(
            MakePaymentRequest paymentRequest, String accessToken) {

        if (validateAccessToken(accessToken) && validateMakePaymentRequest(paymentRequest)) {

            // Here should handle third party payment integration

            // Im just going to assume payment made and insert payment record for payment history
            Double total = paymentRequest.getOrderPrice() + paymentRequest.getShippingCharge();
            Payment payment = new Payment(
                    "pasfksokfffff--adsdd",
                    new Date().toString(),
                    paymentRequest.getUserId(),
                    paymentRequest.getOrderId(),
                    total);

            Payment response = paymentRepository.save(payment);

            var generalResponse = new GeneralResponse<>(
                    HttpStatus.OK.value(),
                    String.format("Successfully paid Rs. %s, your order will arrive soon. " +
                            "Thank you! ~ ABC company", response.getTotalPrice()),
                    new PaymentResponse(
                        response.getId(),
                        response.getPaymentReference(),
                        response.getPaymentMadeAt(),
                        response.getUserId(),
                        response.getOrderId(),
                        response.getTotalPrice()
                    )
            );

            return generalResponse;

        }

        return new GeneralResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized! Failed to proceed with payment.",
                null
        );
    }

    private boolean validateMakePaymentRequest(MakePaymentRequest paymentRequest) {
        return paymentRequest.getOrderId() != null && paymentRequest.getUserId() != null
            && paymentRequest.getOrderId() != 0 && paymentRequest.getUserId() != 0
            && paymentRequest.getOrderPrice() != null && paymentRequest.getOrderPrice() != 0;
    }

    private boolean validateAccessToken(String accessToken) {
        // Dummy validation to prove that validation is here,
        // jsonwebtoken can be use to jwt management
        return (accessToken.contains("Bearer ") && accessToken.length() >= 12 && accessToken.split("\\.").length == 3);
    }

    public GeneralResponse<List<PaymentResponse>> checkPaymentHistory(
            CheckPaymentRequest checkPaymentRequest, String accessToken) {

        if (validateAccessToken(accessToken) && validateCheckPaymentRequest(checkPaymentRequest)) {
            try {
                List<Payment> payments = paymentRepository.findPaymentHistory(
                        checkPaymentRequest.getUserId(),
                        checkPaymentRequest.getOrderId()
                );
                GeneralResponse<List<PaymentResponse>> generalResponse = new GeneralResponse<>(
                        HttpStatus.OK.value(),
                        null,
                        payments.stream().map(py -> new PaymentResponse(py.getId(),
                                py.getPaymentReference(), py.getPaymentMadeAt(), py.getUserId(),
                                py.getOrderId(), py.getTotalPrice())).collect(Collectors.toList())
                );
                return generalResponse;
            } catch (Exception e) {
                Logger.getLogger("info").log(Level.SEVERE, e.getMessage());
                return new GeneralResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Processing failed, Cannot fetch payment history!",
                        null
                );
            }
        }
        return new GeneralResponse<>(
            HttpStatus.UNAUTHORIZED.value(),
            "Unauthorized, failed to process request",
            null
        );
    }

    private boolean validateCheckPaymentRequest(CheckPaymentRequest checkPaymentRequest) {
        return checkPaymentRequest.getOrderId() != null && checkPaymentRequest.getOrderId() != 0
                && checkPaymentRequest.getUserId() != null && checkPaymentRequest.getUserId() != 0;
    }
}

package com.sliit.mtit.PaymentService.service;

import com.sliit.mtit.PaymentService.dto.CheckPaymentRequest;
import com.sliit.mtit.PaymentService.dto.GeneralResponse;
import com.sliit.mtit.PaymentService.dto.MakePaymentRequest;
import com.sliit.mtit.PaymentService.dto.PaymentResponse;
import com.sliit.mtit.PaymentService.entity.Payment;
import com.sliit.mtit.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    private RestTemplate restTemplate;

    // Here goes third part payment integration like Stripe or Paypal or whatever
    public GeneralResponse<PaymentResponse> makePayment(
            MakePaymentRequest paymentRequest, String accessToken) {

        if (checkAuthorization(accessToken) && validateMakePaymentRequest(paymentRequest)) {

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

    public GeneralResponse<PaymentResponse> checkPaymentHistory(
            CheckPaymentRequest checkPaymentRequest, String accessToken) {

        if (checkAuthorization(accessToken) && validateCheckPaymentRequest(checkPaymentRequest)) {
            try {
                Payment payments = paymentRepository.findPaymentHistory(
                        checkPaymentRequest.getUserId(),
                        checkPaymentRequest.getOrderId()
                );
                GeneralResponse<PaymentResponse> generalResponse = new GeneralResponse<>(
                        HttpStatus.OK.value(),
                        null,
                        new PaymentResponse(payments.getId(), payments.getPaymentReference(),
                            payments.getPaymentMadeAt(), payments.getUserId(),
                            payments.getOrderId(), payments.getTotalPrice())
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

    private boolean checkAuthorization(String accessToken) {
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

    public GeneralResponse<List<PaymentResponse>> checkPaymentHistoryByUserId(
            Long userId, String accessToken) {

        if (checkAuthorization(accessToken)) {
            try {
                List<Payment> payments = paymentRepository.findByUserId(userId);
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

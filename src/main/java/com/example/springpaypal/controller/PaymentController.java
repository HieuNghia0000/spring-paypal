package com.example.springpaypal.controller;

import com.example.springpaypal.dto.PaymentDto;
import com.example.springpaypal.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment/create")
    public ResponseEntity<Object> create(@RequestParam("method") String method,
                                         @RequestParam("amount") String amount,
                                         @RequestParam("currency") String currency,
                                         @RequestParam("description") String description) {

        String successUrl = "http://localhost:8080/api/payment/success";
        String cancelUrl = "http://localhost:8080/api/payment/cancel";

        try {
            Payment payment = paymentService.create(new PaymentDto(Double.valueOf(amount),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl));

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(link.getHref());
                }
            }
        }
        catch (PayPalRESTException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.badRequest().body("Error");
    }

    @GetMapping("/payment/success")
    public ResponseEntity<Object> getPaymentSuccess() {
        return ResponseEntity.ok("success");
    }

    @GetMapping("/payment/cancel")
    public ResponseEntity<Object> getPaymentCancel() {
        return ResponseEntity.ok("cancel");
    }
}

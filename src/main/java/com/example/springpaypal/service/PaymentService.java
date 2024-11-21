package com.example.springpaypal.service;

import com.example.springpaypal.dto.PaymentDto;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaymentService {
    Payment create(PaymentDto paymentDto) throws PayPalRESTException;

    Payment execute(String paymentId, String payerId) throws PayPalRESTException;
}

package com.example.springpaypal.service.impl;

import com.example.springpaypal.dto.PaymentDto;
import com.example.springpaypal.service.PaymentService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final APIContext apiContext;

    @Override
    public Payment create(PaymentDto paymentDto) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(paymentDto.getCurrency());
        amount.setTotal(String.format(Locale.forLanguageTag(paymentDto.getCurrency()), "%.2f", paymentDto.getTotal()));

        Transaction transaction = new Transaction();
        transaction.setDescription(paymentDto.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(paymentDto.getMethod());

        Payment payment = new Payment();
        payment.setIntent(paymentDto.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paymentDto.getCancelUrl());
        redirectUrls.setReturnUrl(paymentDto.getSuccessUrl());

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment execute(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);

        return payment.execute(apiContext, execution);
    }

}

package com.example.springpaypal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Double total;

    private String currency;

    private String method;

    private String intent;

    private String description;

    private String cancelUrl;

    private String successUrl;
}

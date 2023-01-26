package com.semicolon.ewallet.kyc.card;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequest {
    private String cardName;
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvv;
}

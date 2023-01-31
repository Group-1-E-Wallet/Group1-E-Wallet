package com.semicolon.ewallet.kyc.card;

import lombok.Data;

@Data
public class CardRequest {
    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
}

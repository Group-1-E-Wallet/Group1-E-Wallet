package com.semicolon.ewallet.sendMoney.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InitiateTransferRequest {
    String bankCode;
    String account_number;
    BigDecimal amount;
    String description;
}

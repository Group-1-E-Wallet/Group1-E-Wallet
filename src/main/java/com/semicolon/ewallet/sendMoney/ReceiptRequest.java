package com.semicolon.ewallet.sendMoney;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptRequest {
    String accountNumber;
    String bankCode;
    BigDecimal amount;
    String description;
}

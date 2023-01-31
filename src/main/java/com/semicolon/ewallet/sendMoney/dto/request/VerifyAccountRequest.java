package com.semicolon.ewallet.sendMoney.dto.request;

import lombok.Data;

@Data
public class VerifyAccountRequest {
    String accountNumber;
    String bankCode;
}

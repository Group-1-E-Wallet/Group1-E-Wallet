package com.semicolon.ewallet.user.sendMoney.dto.request;

import lombok.Data;

@Data
public class VerifyReceiversAccountRequest {
    private String accountNumber;
    private String bankCode;
}

package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class VerifyReceiversAccountRequest {
    private String accountNumber;
    private String bankCode;
}

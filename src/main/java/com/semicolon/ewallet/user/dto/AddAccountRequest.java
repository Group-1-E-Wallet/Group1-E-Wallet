package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class AddAccountRequest {
    private String accountNumber;
    private String bvn;
    private String bankCode;
}

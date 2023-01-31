package com.semicolon.ewallet.user.sendMoney.dto.request;

import lombok.Data;


@Data
public class TransferRecipientRequest {
    private String accountName;
    private String accountNumber;
    private String bankCode;
}

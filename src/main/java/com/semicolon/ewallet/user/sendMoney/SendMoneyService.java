package com.semicolon.ewallet.user.sendMoney;

import com.semicolon.ewallet.user.sendMoney.dto.request.VerifyReceiversAccountRequest;
import com.semicolon.ewallet.user.sendMoney.dto.response.VerifyAccountDeserializedResponse;

import java.io.IOException;

public interface SendMoneyService {
    VerifyAccountDeserializedResponse.Data verifyReceiversAccount(VerifyReceiversAccountRequest verifyReceiversAccountRequest) throws IOException;

}

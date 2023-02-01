package com.semicolon.ewallet.sendMoney;

import com.semicolon.ewallet.sendMoney.dto.request.VerifyAccountRequest;
import com.semicolon.ewallet.sendMoney.dto.response.BankDeserializedResponse;
import com.semicolon.ewallet.sendMoney.dto.response.VerifyAccountDeserializedResponse;

import java.io.IOException;
import java.util.List;

public interface SendMoneyService {
    List<BankDeserializedResponse.Data> listOfBanks() throws IOException;
    VerifyAccountDeserializedResponse.Data verifyReceiverAccount(VerifyAccountRequest verifyAccountRequest) throws IOException;
    Object transferReceipt(ReceiptRequest receiptRequest) throws IOException;
    Object initiateTransfer(ReceiptRequest transferRequest) throws IOException;


}

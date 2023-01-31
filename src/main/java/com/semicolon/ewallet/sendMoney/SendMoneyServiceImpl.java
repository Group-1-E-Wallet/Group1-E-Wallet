package com.semicolon.ewallet.sendMoney;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.ewallet.kyc.card.CardService;
import com.semicolon.ewallet.sendMoney.dto.request.VerifyAccountRequest;
import com.semicolon.ewallet.sendMoney.dto.response.BankDeserializedResponse;
import com.semicolon.ewallet.sendMoney.dto.response.TransferReceiptDeserializedResponse;
import com.semicolon.ewallet.sendMoney.dto.response.VerifyAccountDeserializedResponse;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class SendMoneyServiceImpl implements SendMoneyService{
    @Autowired
    CardService cardService;
    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");
    @Override
    public List<BankDeserializedResponse.Data> listOfBanks() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank")
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .build();

       try( var response = client.newCall(request).execute().body())
       {

           ObjectMapper mapper = new ObjectMapper();
           var returnedMapped = mapper.readTree(response.string());
           return mapper.readValue(returnedMapped.toString(), BankDeserializedResponse.class).getData();
       }
    }


    @Override
    public VerifyAccountDeserializedResponse.Data verifyReceiverAccount(VerifyAccountRequest verifyAccountRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank/resolve?account_number="+
                        verifyAccountRequest.getAccountNumber()+
                        "&bank_code="+verifyAccountRequest.getBankCode())
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .build();

        try( var response = client.newCall(request).execute().body())
        {
            ObjectMapper mapper = new ObjectMapper();
            var returnedMapped = mapper.readTree(response.string());
            return mapper.readValue(returnedMapped.toString(), VerifyAccountDeserializedResponse.class).getData();
        }

    }

    @Override
    public TransferReceiptDeserializedResponse.Data transferReceipt(ReceiptRequest receiptRequest) throws IOException {

        VerifyAccountRequest accountRequest = new VerifyAccountRequest();
        accountRequest.setBankCode(receiptRequest.getBankCode());
        accountRequest.setAccountNumber(receiptRequest.getAccountNumber());

        var foundData = verifyReceiverAccount(accountRequest);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject json = new JSONObject();
        try {
            json.put("type", "nuban");
            json.put("name", foundData.getAccount_name());
            json.put("account_number", receiptRequest.getAccountNumber());
            json.put("bank_code", receiptRequest.getBankCode());
            json.put("currency", "NGN");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transferrecipient")
                .post(body)
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try( var response = client.newCall(request).execute().body())
        {
            ObjectMapper mapper = new ObjectMapper();
            var returnedMapped = mapper.readTree(response.string());
            return mapper.readValue(returnedMapped.toString(), TransferReceiptDeserializedResponse.class).getData();
        }
//
//        {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readTree(response.string());
//        }

    }

    @Override
    public Object initiateTransfer(ReceiptRequest transferRequest) throws IOException {
        var foundReceipt = transferReceipt(transferRequest);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject json = new JSONObject();
        try {
            json.put("source" ,"balance");
            json.put("amount", transferRequest.getAmount());
            json.put("reference", UUID.randomUUID().toString());
            json.put("recipient", foundReceipt.getRecipient_code());
            json.put("reason", transferRequest.getDescription());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transfer")
                .post(body)
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try( var response = client.newCall(request).execute().body())
        {ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.string());
//            return mapper.readValue(returnedMapped.toString(),
//                    TransferInitiationDeserializedResponse.class).getData();
        }
    }

}

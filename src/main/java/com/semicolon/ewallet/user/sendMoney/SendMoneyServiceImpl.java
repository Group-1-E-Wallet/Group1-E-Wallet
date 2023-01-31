package com.semicolon.ewallet.user.sendMoney;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.ewallet.user.sendMoney.dto.request.VerifyReceiversAccountRequest;
import com.semicolon.ewallet.user.sendMoney.dto.response.VerifyAccountDeserializedResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class SendMoneyServiceImpl implements SendMoneyService{
    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");


    @Override
        public VerifyAccountDeserializedResponse.Data verifyReceiversAccount(VerifyReceiversAccountRequest verifyReceiversAccountRequest) throws IOException {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://api.paystack.co/bank/resolve?account_number=" + verifyReceiversAccountRequest.getAccountNumber() + "&bank_code=" + verifyReceiversAccountRequest.getBankCode())
                    .get()
                    .addHeader("Authorization", "Bearer " + SECRET_KEY).build();
            try(var response = client.newCall(request).execute().body()){
                ObjectMapper mapper = new ObjectMapper();
                JsonNode mapped = mapper.readTree(response.string());

                return mapper.readValue(mapped.toString(), VerifyAccountDeserializedResponse.class)
                        .getData();
            }

        }
}

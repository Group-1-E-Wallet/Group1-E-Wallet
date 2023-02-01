package com.semicolon.ewallet.aitimeTopUp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AccessToken {
    private final String CLIENT_SECRET = System.getenv("RELOADLY_CLIENT_SECRET");
    private final String CLIENT_ID = System.getenv("RELOADLY_CLIENT_ID");


    public AccessTokenResponse.Data access() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();
                try {
            json.put("client_id", CLIENT_ID);
            json.put("client_secret", CLIENT_SECRET);
            json.put("grant_type", "client_credentials");
            json.put("audience", "https://topups-sandbox.reloadly.com");
            } catch (
            JSONException e) {
                throw new RuntimeException(e);
            }
            RequestBody body = RequestBody.create(mediaType, json.toString());
        Request request = new Request.Builder()
                .url("https://auth.reloadly.com/oauth/token")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try( var response = client.newCall(request).execute().body())
        {
            ObjectMapper mapper = new ObjectMapper();
            var returnedMapped = mapper.readTree(response.string());
            return mapper.readValue(returnedMapped.toString(), AccessTokenResponse.class).getData();
        }
    }

    public String sendAirtime() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();
        try {
            json.put("operatorId", 62130);
            json.put("amount", 832);
            json.put("useLocalAmount", false);
            json.put("customIdentifier", "airtime-top-up");
            json.put("recipientEmail", "jeanb@reloadly.com");
            json.put("recipientPhone", new JSONObject()
                    .put("countryCode", "NG")
                    .put("number", "09069593520")
            );
            json.put("senderPhone", new JSONObject()
                    .put("countryCode", "NG")
                    .put("number", "11231231231")
            );
        } catch (
                JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://topups-sandbox.reloadly.com/topups")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/com.reloadly.topups-v1+json")
                .addHeader("Authorization", "Bearer <YOUR_TOKEN_HERE>")
                .build();

        Response response = client.newCall(request).execute();
        return response.toString();
    }

}

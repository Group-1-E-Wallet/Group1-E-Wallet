package com.semicolon.ewallet.airtimeTopUp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccessTokenResponse {
    @JsonIgnore
    private boolean status;
    @JsonIgnore
    private String message;
    private Data data;

        @Getter
        @Setter
        public static class Data {
            //private String access_token;
            private String scope;
            private int expires_in;
            private String token_type;
        }
}

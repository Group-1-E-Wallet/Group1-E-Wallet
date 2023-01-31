package com.semicolon.ewallet.user.sendMoney.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class VerifyAccountDeserializedResponse {
    @JsonIgnore
    private boolean status;
    @JsonIgnore
    private String message;
    private  Data data;

    @Getter
    @Setter
    public static class Data{
        @JsonIgnore
        private String account_number;
        private String account_name;
        @JsonIgnore
        private int bank_id;
    }
}

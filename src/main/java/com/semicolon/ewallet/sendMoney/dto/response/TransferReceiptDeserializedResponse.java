package com.semicolon.ewallet.sendMoney.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransferReceiptDeserializedResponse {
    @JsonIgnore
    private boolean status;
    @JsonIgnore
    private String message;
    private Data data;




    @Getter
    @Setter
    public static class Data {

        @JsonIgnore
        private  Boolean active;
        @JsonIgnore
        private String createdAt;
        @JsonIgnore
        private String currency;
        @JsonIgnore
        private  String domain;
        @JsonIgnore
        private Integer id;
        @JsonIgnore
        private  Integer integration;
        @JsonIgnore
        private  String name;
        private  String recipient_code;
        @JsonIgnore
        private String type;
        @JsonIgnore
        private  String updatedAt;
        @JsonIgnore
        private Boolean is_deleted;
        @JsonIgnore
        private Boolean isDeleted;
        @JsonIgnore
        private String description;
        @JsonIgnore
        private String email;
        @JsonIgnore
        private String metadata;
        @JsonIgnore
        private Data.Details details;
            @Getter
            @Setter
            public static  class  Details{
                @JsonIgnore
                private  String authorization_code;
                @JsonIgnore
                private  String account_number;
                @JsonIgnore
                private  String account_name;
                @JsonIgnore
                private  String bank_code;
                @JsonIgnore
                private  String bank_name;
            }


    }

}

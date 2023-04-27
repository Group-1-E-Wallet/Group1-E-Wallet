package com.semicolon.ewallet.sendMoney.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BankDeserializedResponse {
    @JsonIgnore
    private boolean status;
    @JsonIgnore
    private String message;
    private List<Data> data;

    @Getter
    @Setter
    public static class Data {

        private String name;
        @JsonIgnore
        private String slug;
        private String code;
        @JsonIgnore
        private String longcode;
        @JsonIgnore
        private String gateway;
        @JsonIgnore
        private Boolean pay_with_bank;
        @JsonIgnore
        private Boolean active;
        @JsonIgnore
        private Boolean is_deleted;
        @JsonIgnore
        private String country;
        @JsonIgnore
        private String currency;
        @JsonIgnore
        private String type;
        @JsonIgnore
        private int id;
        @JsonIgnore
        private String createdAt;
        @JsonIgnore
        private String updatedAt;
    }
}

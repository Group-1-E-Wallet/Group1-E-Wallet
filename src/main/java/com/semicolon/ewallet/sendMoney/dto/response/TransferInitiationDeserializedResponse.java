package com.semicolon.ewallet.sendMoney.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class TransferInitiationDeserializedResponse {

    Boolean status;
    private String message;
    private Data data;

    @Getter
    @Setter
    public static class Data {
          private  String reference;
          @JsonIgnore private String integration;
          @JsonIgnore private String domain;
          private Integer amount;
          @JsonIgnore private String currency;
          @JsonIgnore  private String source;
          private String reason;
          private Integer recipient;
          private String status;
          private String transfer_code;
          private Integer id;
          private String createdA;
          private String updatedAt;
    }
}

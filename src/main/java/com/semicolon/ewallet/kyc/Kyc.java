package com.semicolon.ewallet.kyc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Kyc {
    @Id
    private String id;
    private  String homeAddress;
    @DBRef
    private Identification identification;
}


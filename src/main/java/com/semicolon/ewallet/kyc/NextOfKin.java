package com.semicolon.ewallet.kyc;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class NextOfKin {
    private String fullName;
    private String emailAddress;
    private String phoneNumber;
    private String Relationship;
}

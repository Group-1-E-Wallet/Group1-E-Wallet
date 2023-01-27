package com.semicolon.ewallet.user.dto;
import com.semicolon.ewallet.kyc.Identification;
import com.semicolon.ewallet.kyc.card.CardRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompleteRegistrationRequest {
    private String emailAddress;
    private Identification identity;
    private String homeAddress;
    private CardRequest cardRequest;
    private String kinEmailAddress;
    private String fullName;
    private String phoneNumber;
    private String Relationship;
}

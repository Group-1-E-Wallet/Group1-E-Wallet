package com.semicolon.ewallet.kyc.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardRequest {
    @NotBlank(message="field is required")
    private String cardName;

    @Pattern(regexp="^\\d+$")
    private String cardNumber;


    @Pattern(regexp="^\\d+$")
    @Size(min=4, max=4, message="min number is 4 max is 4")
    private String expiryDate;

    @Pattern(regexp="^\\d+$")
    @Size(min=3, max=3, message="min number is 4 max is 4")
    private String cvv;
}

package com.semicolon.ewallet.kyc.card;

import com.semicolon.ewallet.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document
@Data
public class Card{
    @Id
    private String id;
    private String cardName;
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvv;
    public Card(String cardName, String cardNumber, LocalDate expiryDate, String cvv) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
}

package com.semicolon.ewallet.kyc.card;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Card{
    @Id
    private String id;
    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    public Card(String cardName, String cardNumber, String expiryDate, String cvv) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
}

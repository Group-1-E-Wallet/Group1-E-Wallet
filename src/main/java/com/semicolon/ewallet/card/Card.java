package com.semicolon.ewallet.card;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document
@Data
public class Card{
    private String cardName;
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvv;
}

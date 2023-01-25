package com.semicolon.ewallet.user;

import com.semicolon.ewallet.user.card.Card;
import com.semicolon.ewallet.user.token.Token;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User{
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String  password;
    private String email;
    private String nin;
    private String address;
    private String nextOfKin;
    private Card card;

    public User(String firstName,String lastName,String email,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}

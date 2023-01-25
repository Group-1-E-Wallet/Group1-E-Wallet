package com.semicolon.ewallet.user;

import com.semicolon.ewallet.user.card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @Generated
    private String id;

    private String firstName;
    private String lastName;
    private String  password;
    private Boolean isVerified;
    private String emailAddress;
    private String nin;
    private String address;
    private String nextOfKin;
    private Card card;
        public User(String firstName,String lastName,String emailAddress,String password){
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.password = password;
        }
}

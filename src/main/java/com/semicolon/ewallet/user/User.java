package com.semicolon.ewallet.user;

import com.semicolon.ewallet.card.Card;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {
        @Id
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

package com.semicolon.ewallet.user;
import com.semicolon.ewallet.kyc.Kyc;
import com.semicolon.ewallet.kyc.NextOfKin;
import com.semicolon.ewallet.kyc.card.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Document
@Data
public class User {
        @Id
        private String id;
        @NotNull(message="this field cannot be empty")
        @NotEmpty
        private String firstName;
        private String lastName;
        private String  password;
        private Boolean isVerified;
        private String emailAddress;
        @DBRef
        private Kyc kyc;
        @DBRef
        private NextOfKin nextOfKin;
        @DBRef
        private List<Card> cards;
        private String nin;
        private String address;
        private Card card;
        public User(String firstName,String lastName,String emailAddress,String password){
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.password = password;
        }

}

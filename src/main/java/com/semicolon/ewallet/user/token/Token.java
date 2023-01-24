package com.semicolon.ewallet.user.token;

import com.semicolon.ewallet.user.User;
import jakarta.annotation.Nonnull;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Token{
    @Id
    private String id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @DBRef
    private User user;


    public Token(String token,LocalDateTime createdAt,LocalDateTime expiredAt,User user){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}

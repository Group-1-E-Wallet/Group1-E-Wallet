package com.semicolon.ewallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EWalletApplication{

    public static void main(String[] args){
        SpringApplication.run(EWalletApplication.class,args);
    }

}

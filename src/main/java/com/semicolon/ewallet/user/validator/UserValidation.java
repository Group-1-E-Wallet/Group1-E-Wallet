package com.semicolon.ewallet.user.validator;

public class UserValidation{
    public static boolean isValidPassword(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*_?&])[A-Za-z\\d@$!%*_?&]{5,20}$");
    }
}

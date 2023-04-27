package com.semicolon.ewallet.registration;

import com.semicolon.ewallet.Exception.ApiResponse;
import com.semicolon.ewallet.registration.dto.LoginRequest;
import com.semicolon.ewallet.registration.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.ResendTokenRequest;
import com.semicolon.ewallet.user.dto.TokenConfirmationRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZonedDateTime;

public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @RequestBody SignUpRequest signUpRequest,
            HttpServletRequest httpServletRequest) throws MessagingException {

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(registrationService.register(signUpRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(registrationService.login(loginRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/resend-token")
    public ResponseEntity<?> resendToken(@RequestBody ResendTokenRequest resendTokenRequest, HttpServletRequest httpServletRequest)
            throws MessagingException{
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(registrationService.resendToken(resendTokenRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/confirm-token")
    public ResponseEntity<?> confirmed(@RequestBody TokenConfirmationRequest tokenConfirmationRequest, HttpServletRequest httpServletRequest) throws MessagingException{
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(registrationService.tokenConfirmation(tokenConfirmationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}

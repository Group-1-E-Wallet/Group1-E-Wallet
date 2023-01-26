package com.semicolon.ewallet.user;

import com.semicolon.ewallet.Exception.ApiResponse;
import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.user.dto.AddAccountRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> signUP(@RequestBody SignUpRequest signUpRequest, HttpServletRequest httpServletRequest) throws MessagingException{


        ApiResponse apiResponse=ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(userService.register(signUpRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping("/bvnMatch")
    public ResponseEntity<?> matchBvn(@RequestBody AddAccountRequest addAccountRequest,
                                      HttpServletRequest httpServletRequest) throws IOException {

        userService.validateBvn(addAccountRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Successful")
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/validateAccount")
    public ResponseEntity<?> accountValidation(@RequestBody CardRequest cardDetailsRequest,
                                               HttpServletRequest httpServletRequest) throws IOException {

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(userService.validateAccount(cardDetailsRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}



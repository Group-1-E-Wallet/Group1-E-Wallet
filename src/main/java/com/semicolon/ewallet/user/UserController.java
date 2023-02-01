package com.semicolon.ewallet.user;
import com.semicolon.ewallet.exception.ApiResponse;
import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.user.dto.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .isSuccessful(true)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .data(userService.forgotPassword(forgotPasswordRequest))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .isSuccessful(true)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .data(userService.resetPassword(resetPasswordRequest))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> resetPassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                           HttpServletRequest httpServletRequest){
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(userService.changePassword(changePasswordRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping("/update-register")
    public ResponseEntity<?> completeRegistration(@RequestBody CompleteRegistrationRequest completeRegistrationRequest,
                                                  HttpServletRequest httpServletRequest) throws IOException{

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(userService.completeRegistration(completeRegistrationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/validateAccount")
    public ResponseEntity<?> accountValidation(@RequestBody CardRequest cardDetailsRequest,
                                               HttpServletRequest httpServletRequest) throws IOException{


        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(userService.validateAccount(cardDetailsRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
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

}

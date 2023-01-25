package com.semicolon.ewallet.user;

import com.semicolon.ewallet.Exception.ApiResponse;
import com.semicolon.ewallet.user.dto.LoginRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/user")
public class Controller{

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUP(@RequestBody SignUpRequest signUpRequest,HttpServletRequest httpServletRequest) throws  MessagingException {
        ApiResponse apiResponse=ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(userService.register(signUpRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(userService.login(loginRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}



package com.semicolon.ewallet.user;

import com.semicolon.ewallet.Exception.ApiResponse;
import com.semicolon.ewallet.user.dto.ChangePasswordRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PatchMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                            HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse apiResponse = ApiResponse.builder()
                //.status(HttpStatus.OK.value())
                .data(userService.changePassword(changePasswordRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}

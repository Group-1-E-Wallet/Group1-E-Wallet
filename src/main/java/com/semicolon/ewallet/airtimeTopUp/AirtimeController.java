package com.semicolon.ewallet.airtimeTopUp;

import com.semicolon.ewallet.exception.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequiredArgsConstructor
public class AirtimeController {

    private final AccessToken accessToken;

    @PostMapping("/token-access")
    public ResponseEntity<?> initiateTransfer(HttpServletRequest httpServletRequest) throws Exception {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(accessToken.access())
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
}

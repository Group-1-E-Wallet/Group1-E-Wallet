package com.semicolon.ewallet.user.sendMoney;

import com.semicolon.ewallet.exception.ApiResponse;
import com.semicolon.ewallet.user.sendMoney.dto.request.VerifyReceiversAccountRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/sendmoney")
public class SendMoneyController {
    @Autowired
    private SendMoneyService sendMoneyService;

    @GetMapping("/verifyreceiversaccount")
    public ResponseEntity<?> verifyReceiversAccount(@RequestBody VerifyReceiversAccountRequest verifyReceiversAccountRequest,
                                                    HttpServletRequest httpServletRequest)throws IOException {
        sendMoneyService.verifyReceiversAccount(verifyReceiversAccountRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(sendMoneyService.verifyReceiversAccount(verifyReceiversAccountRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

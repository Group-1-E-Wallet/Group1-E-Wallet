package com.semicolon.ewallet.sendMoney;

import com.semicolon.ewallet.exception.ApiResponse;
import com.semicolon.ewallet.sendMoney.dto.request.VerifyAccountRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/send")
public class SendMoneyController {
    @Autowired
    SendMoneyService sendMoneyService;

    @GetMapping("/bank-code")
    public ResponseEntity<?> banksCode(HttpServletRequest httpServletRequest) throws Exception {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(sendMoneyService.listOfBanks())
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestBody VerifyAccountRequest verifyAccountRequest, HttpServletRequest httpServletRequest) throws Exception {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(sendMoneyService.verifyReceiverAccount(verifyAccountRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("/receipt")
    public ResponseEntity<?> transferReceipt(@RequestBody ReceiptRequest receiptRequest, HttpServletRequest httpServletRequest) throws Exception {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(sendMoneyService.transferReceipt(receiptRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping("/transfer")
    public ResponseEntity<?> initiateTransfer(@RequestBody ReceiptRequest transferRequest, HttpServletRequest httpServletRequest) throws Exception {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(sendMoneyService.initiateTransfer(transferRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }



}

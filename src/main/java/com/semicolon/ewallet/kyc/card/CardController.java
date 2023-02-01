package com.semicolon.ewallet.kyc.card;

import com.semicolon.ewallet.exception.ApiResponse;
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
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/add-card")
    public ResponseEntity<?> addCard(@RequestBody @Valid CardRequest cardRequest,
                                     HttpServletRequest httpServletRequest)throws IOException{
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cardService.addCard(cardRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/verifyCard")
    public ResponseEntity<?> accountValidation(@RequestBody @Valid CardRequest addCardRequest,
                                               HttpServletRequest httpServletRequest) throws IOException{

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cardService.validateAccount(addCardRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(@PathVariable String cardId, @RequestBody CardRequest cardRequest,
                                        HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cardService.updateCard(cardId, cardRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/viewcard")
    public ResponseEntity<?> viewAllCard(HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cardService.viewAllCard())
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id,  HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cardService.viewId(id))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("/deleteCard")
    public ResponseEntity<?> deleteCard(@RequestBody DeleteCardRequest deleteCardRequest, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(cardService.deleteCard(deleteCardRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }




}

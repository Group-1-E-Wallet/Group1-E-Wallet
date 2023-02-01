package com.semicolon.ewallet.kyc.card;
import com.semicolon.ewallet.exception.RegistrationException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class CardServiceImpl implements CardService{

    @Autowired
    CardRepository cardRepository;
    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");

    @Override
    public Card addCard(CardRequest cardRequest){
        if(cardRepository.findByCardNumberIgnoreCase(cardRequest.getCardNumber())
                .isPresent()) throw new RuntimeException("Card already exist");
try{
    validateAccount(cardRequest);
}catch (IOException e){
    log.info("Problem: "+ e.getMessage());
    throw new RuntimeException();
}
        Card card = new Card(
                cardRequest.getCardName(),
                cardRequest.getCardNumber(),
                cardRequest.getExpiryDate(),
                cardRequest.getCvv()
        );

        log.info(cardRequest.getCardName());
               return cardRepository.save(card);
    }
    @Override
    public String validateAccount (CardRequest addCardRequest) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/decision/bin/"
                        + addCardRequest.getCardNumber().substring(0, 6))
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    @Override
    public String deleteCard(DeleteCardRequest deleteCardRequest) {
        var  foundCard = cardRepository.findById(deleteCardRequest.getId())
                .orElseThrow(() -> new RegistrationException("Unable to delete Card"));
        cardRepository.deleteById(foundCard.getId());
        return "Card Deleted";
    }

    @Override
    public String updateCard(String id, CardRequest cardRequest) {
        var foundCard = cardRepository.findById(id).orElseThrow(()-> new RegistrationException("invalid card"));
        if (foundCard != null) {
            foundCard.setCardName(cardRequest.getCardName());
            foundCard.setCardNumber(cardRequest.getCardNumber());
            foundCard.setExpiryDate(cardRequest.getExpiryDate());
            foundCard.setCvv(cardRequest.getCvv());
        }

        cardRepository.save(foundCard);
        return "card updated";
    }

    @Override
    public List<Card> viewAllCard() {
        return cardRepository.findAll();
    }

   @Override

    public Card viewId(String id) {
        return cardRepository.findById(id).orElseThrow(()-> new RegistrationException("invalid card"));
    }


}


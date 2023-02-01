package com.semicolon.ewallet.kyc.card;
import com.semicolon.ewallet.exception.RegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CardServiceImpl implements CardService{

    @Autowired
    CardRepository cardRepository;

    @Override
    public Card addCard(CardRequest cardRequest) {
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
    public String deleteCard(String id) {
        var card = cardRepository.findById(id).orElseThrow(()-> new RegistrationException("invalid card"));

        return "delete update";
    }

    @Override
    public String updateCard(String id, CardRequest cardRequest) {
        var foundCard = cardRepository.findById(id).orElseThrow(()-> new RegistrationException("invalid card"));
        if (foundCard.getCardName() != null) {
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
}


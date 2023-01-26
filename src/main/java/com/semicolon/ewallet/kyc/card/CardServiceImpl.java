package com.semicolon.ewallet.kyc.card;

import com.semicolon.ewallet.Exception.RegistrationException;
import com.semicolon.ewallet.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService{

    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserService userService;

    @Override
    public Card addCard(CardRequest cardRequest) {
        Card card = new Card(
                cardRequest.getCardName(),
                cardRequest.getCardNumber(),
                cardRequest.getExpiryDate(),
                cardRequest.getCvv()
        );
        cardRepository.save(card);

        return card;
    }

    @Override
    public String deleteCard(String id) {
        var card = cardRepository.findByCardId(id).orElseThrow(()-> new RegistrationException("invalid card"));

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


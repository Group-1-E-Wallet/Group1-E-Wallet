package com.semicolon.ewallet.kyc.card;
import com.semicolon.ewallet.exception.RegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    //THIS METHOD IS SUPPOSED TO DELETE CARD BUT IT KEEPS RETURNING THE CARD IF YOU START LOOKING FOR IT IN THE DATABASE
//    @Override
//    public String deleteCard(DeleteCardRequest deleteCardRequest) {
//        var foundCard = cardRepository.findById(deleteCardRequest.getId());
//        String randomNumber = UUID.randomUUID().toString();
//        foundCard.get().setCardNumber("deleted"+deleteCardRequest.getId()+randomNumber);
//        cardRepository.save(foundCard.get());
//        return "delete update";
//    }

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
    public Optional<Card> findById(String id) {
       var foundCard = cardRepository.findById(id);
       if (!foundCard.isPresent()) throw new IllegalStateException("Card not found");
        return foundCard;
    }


}


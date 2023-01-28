package com.semicolon.ewallet.kyc.card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Card addCard(CardRequest cardRequest);
   // String deleteCard(DeleteCardRequest deleteCardRequest);

    String deleteCard(String id);

    String updateCard(String id,CardRequest cardRequest);
    List<Card> viewAllCard();

  //  Optional<Card> findById(String id);
}

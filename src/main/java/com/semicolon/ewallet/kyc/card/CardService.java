package com.semicolon.ewallet.kyc.card;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CardService {
    Card addCard(CardRequest cardRequest) throws IOException;

    String deleteCard(DeleteCardRequest deleteCardRequest);

    String updateCard(String id,CardRequest cardRequest);
    List<Card> viewAllCard();
    String validateAccount(CardRequest cardRequest) throws IOException;
    Card viewId(String id);
}

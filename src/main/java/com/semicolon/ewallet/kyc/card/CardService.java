package com.semicolon.ewallet.kyc.card;

import java.util.List;

public interface CardService {
    com.semicolon.ewallet.user.card.Card addCard(CardRequest cardRequest);
    String deleteCard(String id);
    String updateCard(String id, CardRequest cardRequest);
    List<Card> viewAllCard();
}

package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Card;
import uy.edu.um.wtf.repository.CardRepository;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public void addCard(Card card) {
        if (cardRepository.findByCardNumber(card.getCardNumber()).isPresent()) {
            throw new RuntimeException("Card with that number already exists");
        }
        if (!validCardNumber(card.getCardNumber())) {
            throw new RuntimeException("Invalid card number");
        }
        if (!validExpirationDate(card.getExpirationMonth(), card.getExpirationYear())) {
            throw new RuntimeException("Invalid expiration date");
        }
        if (card.getCvv() < 100 || card.getCvv() > 999) {
            throw new RuntimeException("Invalid CVV");
        }
        cardRepository.save(card);
    }

    public boolean validCardNumber(Long cardNumber) {
        String cardNumberRegex = "^[0-9]{16}$";
        return cardNumber.toString().matches(cardNumberRegex);
    }

    public boolean validExpirationDate(int expirationMonth, int expirationYear) {
        if (expirationMonth < 1 || expirationMonth > 12) {
            return false;
        }
        if (expirationYear < 2023) {
            return false;
        }
        return true;
    }
}

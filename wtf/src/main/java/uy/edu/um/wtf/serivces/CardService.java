package uy.edu.um.wtf.serivces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.Card;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.repository.CardRepository;
import uy.edu.um.wtf.repository.MovieRepository;
import uy.edu.um.wtf.repository.UserRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;


@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

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

//    public void updateCard(Long userId,Long cardNumber, Map<String, String> cambios) {
//        User usuario = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
//
//        Card card = cardRepository.findById(cardNumber)
//                .orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada con el número: " + cardNumber));
//
//        cambios.forEach((campo, valor) -> {
//            switch (campo) {
//                case "expirationMonth":
//                    int expirationMonth = Integer.parseInt(valor);
//                    if (expirationMonth < 1 || expirationMonth > 12) {
//                        throw new IllegalArgumentException("Mes de expiración inválido");
//                    }
//                    card.setExpirationMonth(expirationMonth);
//                    break;
//
//                case "expirationYear":
//                    int expirationYear = Integer.parseInt(valor);
//                    if (expirationYear < LocalDate.now().getYear()) {
//                        throw new IllegalArgumentException("Año de expiración inválido");
//                    }
//                    card.setExpirationYear(expirationYear);
//                    break;
//
//                case "cvv":
//                    int cvv = Integer.parseInt(valor);
//                    if (cvv < 100 || cvv > 999) {
//                        throw new IllegalArgumentException("CVV inválido");
//                    }
//                    card.setCvv(cvv);
//                    break;
//                case "userId":
//                    usuario.setCa
//
//
//
//                default:
//                    throw new IllegalArgumentException("Campo no válido: " + campo);
//            }
//        });
//
//        cardRepository.save(card);
//    }
}

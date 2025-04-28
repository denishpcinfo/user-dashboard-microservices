package com.d3n15.credit_card_service.service;

import com.d3n15.credit_card_service.dto.CreditCardDTO;
import com.d3n15.credit_card_service.entity.CreditCardEntity;
import com.d3n15.credit_card_service.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCardDTO> getCardsByUserId(String userId) {
        List<CreditCardEntity> cards = creditCardRepository.findByUserId(userId);
        return cards.stream()
                .map(card -> new CreditCardDTO(card.getBrand(), card.getMaskedNumber()))
                .collect(Collectors.toList());
    }
}
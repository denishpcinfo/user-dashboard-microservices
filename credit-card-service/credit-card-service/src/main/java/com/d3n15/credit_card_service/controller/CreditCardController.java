package com.d3n15.credit_card_service.controller;

import com.d3n15.credit_card_service.dto.CreditCardDTO;
import com.d3n15.credit_card_service.service.CreditCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/{userId}")
    public List<CreditCardDTO> getCards(@PathVariable String userId) {
        return creditCardService.getCardsByUserId(userId);
    }
}
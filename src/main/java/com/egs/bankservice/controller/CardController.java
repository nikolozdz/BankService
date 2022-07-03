package com.egs.bankservice.controller;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.card.AddCardRequest;
import com.egs.bankservice.model.card.AddCardResponse;
import com.egs.bankservice.model.card.CardResponse;
import com.egs.bankservice.model.card.SetAuthMethodRequest;
import com.egs.bankservice.service.card.CardService;
import com.egs.bankservice.shared.dto.CardDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("add")
    public AddCardResponse addCard(@RequestBody AddCardRequest addCardRequest) throws BankServiceException {
        CardDto cardDto = cardService.addCard(addCardRequest);
        AddCardResponse response = new AddCardResponse();
        BeanUtils.copyProperties(cardDto, response);
        return response;
    }

    @GetMapping("get/{id}")
    public CardResponse getCardById(@PathVariable(value = "id") long id) throws BankServiceException {
        CardDto cardDto =  cardService.getCardById(id);
        CardResponse response = new CardResponse();
        BeanUtils.copyProperties(cardDto, response);
        return response;
    }

    @GetMapping("getByCardNumber")
    public CardResponse getCardByNumber(@RequestParam(value = "cardNumber") String cardNumber) throws BankServiceException {
        CardDto cardDto =  cardService.getCardByNumber(cardNumber);
        CardResponse response = new CardResponse();
        BeanUtils.copyProperties(cardDto, response);
        response.setUserPersonalId(cardDto.getUser().getPersonalId());
        return response;
    }

    @DeleteMapping("delete/{id}")
    public void deleteCard(@PathVariable(value = "id") long id) throws BankServiceException {
        cardService.deleteCard(id);
    }

    @PostMapping("setAuthMethod")
    public void setAuthMethodByCardNumber(@RequestBody SetAuthMethodRequest setAuthMethodRequest) throws BankServiceException {
        cardService.setAuthMethodByCardNumber(setAuthMethodRequest);
    }

    @PostMapping("unblock")
    public void unblockCard(@RequestParam(value = "cardNumber") String cardNumber) throws BankServiceException {
        cardService.unblockCard(cardNumber);
    }
}


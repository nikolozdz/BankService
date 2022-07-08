package com.egs.bankservice.controller.implementation;

import com.egs.bankservice.controller.CardController;
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
public class CardControllerImpl implements CardController {

    private final CardService cardService;

    @Autowired
    public CardControllerImpl(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("add")
    @Override
    public AddCardResponse addCard(@RequestBody AddCardRequest addCardRequest) throws BankServiceException {
        CardDto cardDto = cardService.addCard(addCardRequest);
        AddCardResponse response = new AddCardResponse();
        BeanUtils.copyProperties(cardDto, response);
        return response;
    }

    @GetMapping("get/{id}")
    @Override
    public CardResponse getCardById(@PathVariable(value = "id") long id) throws BankServiceException {
        CardDto cardDto =  cardService.getCardById(id);
        CardResponse response = new CardResponse();
        BeanUtils.copyProperties(cardDto, response);
        return response;
    }

    @GetMapping("getByCardNumber")
    @Override
    public CardResponse getCardByNumber(@RequestParam(value = "cardNumber") String cardNumber) throws BankServiceException {
        CardDto cardDto =  cardService.getCardByNumber(cardNumber);
        CardResponse response = new CardResponse();
        BeanUtils.copyProperties(cardDto, response);
        response.setUserPersonalId(cardDto.getUser().getPersonalId());
        return response;
    }

    @DeleteMapping("delete/{id}")
    @Override
    public void deleteCard(@PathVariable(value = "id") long id) throws BankServiceException {
        cardService.deleteCard(id);
    }

    @PostMapping("setAuthMethod")
    @Override
    public void setAuthMethodByCardNumber(@RequestBody SetAuthMethodRequest setAuthMethodRequest) throws BankServiceException {
        cardService.setAuthMethodByCardNumber(setAuthMethodRequest);
    }

    @PostMapping("unblock")
    @Override
    public void unblockCard(@RequestParam(value = "cardNumber") String cardNumber) throws BankServiceException {
        cardService.unblockCard(cardNumber);
    }
}


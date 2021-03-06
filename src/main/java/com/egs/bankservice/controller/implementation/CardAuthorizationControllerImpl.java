package com.egs.bankservice.controller.implementation;

import com.egs.bankservice.controller.CardAuthorizationController;
import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.auth.CardAuthRequest;
import com.egs.bankservice.model.auth.CardAuthResponse;
import com.egs.bankservice.model.auth.ValidateCardResponse;
import com.egs.bankservice.service.auth.CardAuthService;
import com.egs.bankservice.service.auth.CardAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/cardAuth")
public class CardAuthorizationControllerImpl implements CardAuthorizationController {

    public static final String HTTP_SESSION_CARD_NUMBER_KEY = "cardNumber";

    private final CardAuthService cardAuthService;

    @Autowired
    public CardAuthorizationControllerImpl(CardAuthServiceImpl cardAuthService) {
        this.cardAuthService = cardAuthService;
    }


    @GetMapping("validateCard")
    @Override
    public ValidateCardResponse validateCard(HttpSession httpSession, @RequestParam String cardNumber) throws BankServiceException {
        return cardAuthService.validateCard(cardNumber);
    }


    @PostMapping("auth")
    @Override
    public CardAuthResponse cardAuth(HttpSession httpSession, @RequestBody CardAuthRequest cardAuthRequest) throws BankServiceException {
        CardAuthResponse cardAuthResponse = cardAuthService.cardAuth(cardAuthRequest);
        httpSession.setAttribute(HTTP_SESSION_CARD_NUMBER_KEY, cardAuthResponse.getCardNumber());
        return cardAuthResponse;
    }

    @PostMapping("closeSession")
    @Override
    public void closeSession(HttpSession httpSession) {
        httpSession.invalidate();
    }
}

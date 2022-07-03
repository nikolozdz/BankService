package com.egs.bankservice.controller;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.card.DepositRequest;
import com.egs.bankservice.model.card.WithdrawRequest;
import com.egs.bankservice.service.cardaction.CardActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@RestController
@RequestMapping("api/cards/action")
public class CardActionController {

    @Autowired
    private CardActionService cardActionService;

    @GetMapping("checkBalance")
    public BigDecimal checkBalance(HttpSession httpSession, @RequestParam(value = "cardNumber") String cardNumber) throws BankServiceException {
        checkSession(httpSession, cardNumber);
        return cardActionService.checkBalance(cardNumber);
    }

    @PostMapping("deposit")
    public void deposit(HttpSession httpSession, @RequestBody DepositRequest depositRequest) throws BankServiceException {
        checkSession(httpSession, depositRequest.getCardNumber());
        cardActionService.deposit(depositRequest.getCardNumber(), depositRequest.getAmount());
    }

    @PostMapping("withdraw")
    public void withdrawal(HttpSession httpSession, @RequestBody WithdrawRequest withdrawalRequest) throws BankServiceException {
        checkSession(httpSession, withdrawalRequest.getCardNumber());
        cardActionService.withdraw(withdrawalRequest.getCardNumber(), withdrawalRequest.getAmount());
    }

    private void checkSession(HttpSession httpSession, String cardNumber) throws BankServiceException {
        if (!cardNumber.equals(httpSession.getAttribute(CardAuthorizationController.HTTP_SESSION_CARD_NUMBER_KEY))) {
            throw new BankServiceException("Session is not initialized");
        }
    }
}


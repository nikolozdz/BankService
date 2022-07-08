package com.egs.bankservice.controller.implementation;

import com.egs.bankservice.controller.CardActionController;
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
public class CardActionControllerImpl implements CardActionController {

    private final CardActionService cardActionService;

    @Autowired
    public CardActionControllerImpl(CardActionService cardActionService) {
        this.cardActionService = cardActionService;
    }

    @GetMapping("checkBalance")
    @Override
    public BigDecimal checkBalance(HttpSession httpSession, @RequestParam(value = "cardNumber") String cardNumber) throws BankServiceException {
        checkSession(httpSession, cardNumber);
        return cardActionService.checkBalance(cardNumber);
    }

    @PostMapping("deposit")
    @Override
    public void deposit(HttpSession httpSession, @RequestBody DepositRequest depositRequest) throws BankServiceException {
        checkSession(httpSession, depositRequest.getCardNumber());
        cardActionService.deposit(depositRequest.getCardNumber(), depositRequest.getAmount());
    }

    @PostMapping("withdraw")
    @Override
    public void withdrawal(HttpSession httpSession, @RequestBody WithdrawRequest withdrawalRequest) throws BankServiceException {
        checkSession(httpSession, withdrawalRequest.getCardNumber());
        cardActionService.withdraw(withdrawalRequest.getCardNumber(), withdrawalRequest.getAmount());
    }

    private void checkSession(HttpSession httpSession, String cardNumber) throws BankServiceException {
        if (!cardNumber.equals(httpSession.getAttribute(CardAuthorizationControllerImpl.HTTP_SESSION_CARD_NUMBER_KEY))) {
            throw new BankServiceException("Session is not initialized");
        }
    }
}


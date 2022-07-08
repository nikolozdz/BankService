package com.egs.bankservice.controller;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.card.AddCardRequest;
import com.egs.bankservice.model.card.AddCardResponse;
import com.egs.bankservice.model.card.CardResponse;
import com.egs.bankservice.model.card.SetAuthMethodRequest;

public interface CardController {

    AddCardResponse addCard(AddCardRequest addCardRequest) throws BankServiceException;

    CardResponse getCardById(long id) throws BankServiceException;

    CardResponse getCardByNumber(String cardNumber) throws BankServiceException;

    void deleteCard(long id) throws BankServiceException;

    void setAuthMethodByCardNumber(SetAuthMethodRequest setAuthMethodRequest) throws BankServiceException;

    void unblockCard(String cardNumber) throws BankServiceException;
}

package com.egs.bankservice.service.card;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.card.AddCardRequest;
import com.egs.bankservice.model.card.SetAuthMethodRequest;
import com.egs.bankservice.shared.dto.CardDto;

public interface CardService {

    CardDto addCard(AddCardRequest addCardRequest) throws BankServiceException;

    CardDto getCardById(long id) throws BankServiceException;

    CardDto getCardByNumber(String number) throws BankServiceException;

    void deleteCard(long id) throws BankServiceException;

    void setAuthMethodByCardNumber(SetAuthMethodRequest setAuthMethodRequest) throws BankServiceException;

    void unblockCard(String cardNumber) throws BankServiceException;
}

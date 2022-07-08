package com.egs.bankservice.controller;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.auth.CardAuthRequest;
import com.egs.bankservice.model.auth.CardAuthResponse;
import com.egs.bankservice.model.auth.ValidateCardResponse;

import javax.servlet.http.HttpSession;

public interface CardAuthorizationController {

    ValidateCardResponse validateCard(HttpSession httpSession, String cardNumber) throws BankServiceException;

    CardAuthResponse cardAuth(HttpSession httpSession, CardAuthRequest cardAuthRequest) throws BankServiceException;

    void closeSession(HttpSession httpSession);
}

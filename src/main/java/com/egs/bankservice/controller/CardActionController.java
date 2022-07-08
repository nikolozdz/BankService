package com.egs.bankservice.controller;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.card.DepositRequest;
import com.egs.bankservice.model.card.WithdrawRequest;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public interface CardActionController {

    BigDecimal checkBalance(HttpSession httpSession, String cardNumber) throws BankServiceException;

    void deposit(HttpSession httpSession, DepositRequest depositRequest) throws BankServiceException;

    void withdrawal(HttpSession httpSession,  WithdrawRequest withdrawalRequest) throws BankServiceException;

}

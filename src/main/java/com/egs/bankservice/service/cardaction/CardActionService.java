package com.egs.bankservice.service.cardaction;

import com.egs.bankservice.exception.BankServiceException;

import java.math.BigDecimal;

public interface CardActionService {

    BigDecimal checkBalance(String cardNumber) throws BankServiceException;

    void deposit(String cardNumber, BigDecimal amount) throws BankServiceException;

    void withdraw(String cardNumber, BigDecimal amount) throws BankServiceException;
}

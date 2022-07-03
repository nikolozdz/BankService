package com.egs.bankservice.service.cardaction;

import com.egs.bankservice.entity.card.Card;
import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.shared.utils.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class CardActionServiceImpl implements CardActionService {

    private final Logger log = LoggerFactory.getLogger(CardActionServiceImpl.class);

    @Autowired
    private CardRepository cardRepository;

    @Override
    @Transactional
    public BigDecimal checkBalance(String cardNumber) throws BankServiceException {
        return getCard(cardNumber).getAmount();
    }

    @Override
    @Transactional
    public void deposit(String cardNumber, BigDecimal amount) throws BankServiceException {
        Card card = getCard(cardNumber);
        card.setAmount(card.getAmount().add(amount));
    }

    @Override
    @Transactional
    public void withdraw(String cardNumber, BigDecimal amount) throws BankServiceException {
        Card card = getCard(cardNumber);
        if (card.getAmount().compareTo(amount) < 0) {
            String errorMessage = "Operation cannot be done: current balance is less then withdraw amount";
            log.warn(errorMessage);
            throw new BankServiceException(errorMessage);
        }
        card.setAmount(card.getAmount().subtract(amount));
    }

    private Card getCard(String cardNumber) throws BankServiceException {
        Card card = cardRepository.getCardByCardNumber(cardNumber);
        if (card == null) {
            String errorMessage = "Can't find card with cardNumber = [" + cardNumber + "] in database";
            log.warn(errorMessage);
            throw new BankServiceException(errorMessage);
        }
        return card;
    }
}

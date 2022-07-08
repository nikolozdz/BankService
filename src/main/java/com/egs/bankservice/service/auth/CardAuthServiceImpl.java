package com.egs.bankservice.service.auth;

import com.egs.bankservice.entity.card.AuthMethod;
import com.egs.bankservice.entity.card.Card;
import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.auth.CardAuthRequest;
import com.egs.bankservice.model.auth.CardAuthResponse;
import com.egs.bankservice.model.auth.ValidateCardResponse;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.shared.utils.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class CardAuthServiceImpl implements CardAuthService {

    private final Logger log = LoggerFactory.getLogger(CardAuthServiceImpl.class);

    private final CardRepository cardRepository;

    private final CardAuthInfoService cardAuthInfoService;

    @Autowired
    public CardAuthServiceImpl(CardRepository cardRepository, CardAuthInfoService cardAuthInfoService) {
        this.cardRepository = cardRepository;
        this.cardAuthInfoService = cardAuthInfoService;
    }


    @Override
    public ValidateCardResponse validateCard(String cardNumber) throws BankServiceException {
        Card card = getCard(cardNumber);
        return new ValidateCardResponse(cardNumber, card.getAuthMethod());
    }

    @Override
    public CardAuthResponse cardAuth(CardAuthRequest cardAuthRequest) throws BankServiceException {
        Card card = getCard(cardAuthRequest.getCardNumber());
        String errorMessage;
        if (card.getAuthInfo().isBlocked()){
            errorMessage = "Authorization failed: card is blocked";
            log.warn(errorMessage);
            throw new BankServiceException(errorMessage);
        }

        String cardAuth = (card.getAuthMethod() == AuthMethod.PIN) ? card.getPin() : card.getFingerprint();
        boolean passwordMatches = cardAuth.equals(Encoder.sha1Encode(cardAuthRequest.getCode()));

        if (!passwordMatches){
            cardAuthInfoService.increaseFailedAttempts(card);
            errorMessage = "Authorization failed: " + ((card.getAuthMethod() == AuthMethod.PIN) ? "PIN is incorrect" : "FingerPrint cannot be recognized");
            log.warn(errorMessage);
            throw new BankServiceException(errorMessage);
        }

        card.getAuthInfo().setFailedAttempts(0);

        CardAuthResponse cardAuthResponse = new CardAuthResponse();
        cardAuthResponse.setCardNumber(card.getCardNumber());
        cardAuthResponse.setAllowedActions(Stream.of(CardAllowedAction.values())
                .map(Enum::name)
                .collect(Collectors.toList()));

        return cardAuthResponse;
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

    private enum CardAllowedAction {
        CHECK_BALANCE,
        DEPOSIT,
        WITHDRAW
    }
}

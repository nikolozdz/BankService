package com.egs.bankservice.service.card;

import com.egs.bankservice.entity.card.AuthMethod;
import com.egs.bankservice.entity.card.Card;
import com.egs.bankservice.entity.card.CardAuthInfo;
import com.egs.bankservice.entity.user.User;
import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.card.AddCardRequest;
import com.egs.bankservice.model.card.SetAuthMethodRequest;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.repository.UserRepository;
import com.egs.bankservice.shared.dto.CardDto;
import com.egs.bankservice.shared.utils.Encoder;
import com.egs.bankservice.shared.utils.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    @Value("${cardNumber.length}")
    private int cardNumberLength;

    @Value("${cardNumber.prefix}")
    private int prefix;

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CardDto addCard(AddCardRequest addCardRequest) throws BankServiceException {
        User user = userRepository.getUserByPersonalId(addCardRequest.getPersonalId());
        if (user == null) {
            String errorMessage = "User by personalId = " + addCardRequest.getPersonalId() + " does not exist";
            log.error(errorMessage);
            throw new BankServiceException(errorMessage);
        }
        Card card = new Card();
        card.setAuthMethod(getAuthMethod(addCardRequest.getAuthMethod()));
        card.setFingerprint(Encoder.sha1Encode(addCardRequest.getFingerprint()));
        card.setUser(user);
        String pin = RandomGenerator.generatePin();
        card.setPin(Encoder.sha1Encode(pin));
        card.setAuthInfo(new CardAuthInfo());
        String cardNumber = RandomGenerator.generateCardNumber(prefix, cardNumberLength);
        while (cardRepository.existsByCardNumber(cardNumber)) {
            cardNumber = RandomGenerator.generateCardNumber(prefix, cardNumberLength);
        }
        card.setCardNumber(cardNumber);
        cardRepository.save(card);
        CardDto cardDto = new CardDto();
        BeanUtils.copyProperties(card, cardDto);
        cardDto.setPin(pin);
        return cardDto;
    }

    @Override
    public CardDto getCardById(long id) throws BankServiceException {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent()) {
            String errorMessage = "Can't find card By id: " + id;
            log.warn(errorMessage);
            throw new BankServiceException(errorMessage);
        }
        CardDto cardDto = new CardDto();
        BeanUtils.copyProperties(optionalCard.get(), cardDto);
        return cardDto;
    }

    @Override
    public CardDto getCardByNumber(String cardNumber) throws BankServiceException {
        Card card = getCard(cardNumber);
        CardDto cardDto = new CardDto();
        BeanUtils.copyProperties(card, cardDto);
        return cardDto;
    }

    @Override
    @Transactional
    public void deleteCard(long id) throws BankServiceException {
        if (!cardRepository.existsById(id)) {
            String errorMessage = "There is no card with id: " + id;
            log.warn(errorMessage);
            throw new BankServiceException(errorMessage);
        }
        cardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void setAuthMethodByCardNumber(SetAuthMethodRequest setAuthMethodRequest) throws BankServiceException {
        Card card = getCard(setAuthMethodRequest.getCardNumber());
        AuthMethod cardAuthMethod = getAuthMethod(setAuthMethodRequest.getAuthMethod());
        card.setAuthMethod(cardAuthMethod);
    }

    @Override
    @Transactional
    public void unblockCard(String cardNumber) throws BankServiceException {
        Card card = getCard(cardNumber);
        card.getAuthInfo().setBlocked(false);
        card.getAuthInfo().setFailedAttempts(0);
    }

    private AuthMethod getAuthMethod(String cardAuthMethod) throws BankServiceException {
        AuthMethod authMethod;
        try {
            authMethod = AuthMethod.valueOf(cardAuthMethod);
        } catch (IllegalArgumentException ex) {
            String errorMessage = "Not supported card auth method: " + cardAuthMethod;
            log.warn(errorMessage);
            throw new BankServiceException("Not supported card auth method: " + cardAuthMethod);
        }
        return authMethod;
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

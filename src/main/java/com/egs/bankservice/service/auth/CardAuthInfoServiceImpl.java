package com.egs.bankservice.service.auth;

import com.egs.bankservice.entity.card.Card;
import com.egs.bankservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CardAuthInfoServiceImpl implements CardAuthInfoService {

    @Value("${card.allowedFailedAttempts}")
    int allowedFailedAttempts;

    @Autowired
    private CardRepository cardRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Card increaseFailedAttempts(Card card) {
        int failedAttempts = card.getAuthInfo().getFailedAttempts();
        card.getAuthInfo().setFailedAttempts(++failedAttempts);
        if (card.getAuthInfo().getFailedAttempts() >= allowedFailedAttempts) {
            card.getAuthInfo().setBlocked(true);
        }

        return cardRepository.save(card);
    }
}

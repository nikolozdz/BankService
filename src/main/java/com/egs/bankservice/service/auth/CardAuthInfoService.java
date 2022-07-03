package com.egs.bankservice.service.auth;

import com.egs.bankservice.entity.card.Card;

public interface CardAuthInfoService {
    Card increaseFailedAttempts(Card card);
}

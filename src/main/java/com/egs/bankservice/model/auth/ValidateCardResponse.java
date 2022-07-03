package com.egs.bankservice.model.auth;

import com.egs.bankservice.entity.card.AuthMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateCardResponse {

    private String cardNumber;

    private AuthMethod authMethod;

    public ValidateCardResponse(String cardNumber, AuthMethod authMethod) {
        this.cardNumber = cardNumber;
        this.authMethod = authMethod;
    }
}

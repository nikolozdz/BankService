package com.egs.bankservice.model.auth;

import com.egs.bankservice.entity.card.AuthMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardAuthResponse {

    public CardAuthResponse(String cardNumber, AuthMethod authMethod, List<String> allowedActions) {
        this.cardNumber = cardNumber;
        this.authMethod = authMethod;
        this.allowedActions = allowedActions;
    }

    public CardAuthResponse() {}

    private String cardNumber;

    private AuthMethod authMethod;

    private List<String> allowedActions = new ArrayList<>();


}


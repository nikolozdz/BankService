package com.egs.bankservice.model.card;

import com.egs.bankservice.entity.card.AuthMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {

    private long id;

    private String userPersonalId;

    private String cardNumber;

    private AuthMethod authMethod;;
}

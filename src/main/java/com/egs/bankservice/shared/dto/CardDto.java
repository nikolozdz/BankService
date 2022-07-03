package com.egs.bankservice.shared.dto;

import com.egs.bankservice.entity.card.AuthMethod;
import com.egs.bankservice.entity.card.CardAuthInfo;
import com.egs.bankservice.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CardDto {

    private long id;

    private String cardNumber;

    private AuthMethod authMethod;

    private String fingerprint;

    private String pin;

    private User user;

    private BigDecimal amount;

    private CardAuthInfo authInfo;
}
package com.egs.bankservice.entity.card;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class CardAuthInfo {

    @Column(nullable = false)
    private boolean blocked = false;

    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts = 0;
}

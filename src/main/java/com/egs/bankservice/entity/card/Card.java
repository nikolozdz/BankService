package com.egs.bankservice.entity.card;

import com.egs.bankservice.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "card")
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 30, unique = true, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private AuthMethod authMethod;

    @Column(length = 200)
    private String fingerprint;

    @Column(length = 50)
    private String pin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private BigDecimal amount = new BigDecimal("0");

    @Embedded
    private CardAuthInfo authInfo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

}
